package io.gradeshift.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.widget.textChanges
import com.jakewharton.rxrelay.PublishRelay
import com.jakewharton.rxrelay.SerializedRelay
import io.gradeshift.GradesApplication
import io.gradeshift.ui.common.ext.snackBar
import io.gradeshift.ui.common.ext.ui
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import rx.Observable
import rx.Subscription
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginPresenter.View {
    @Inject lateinit var ui: LoginUI
    @Inject lateinit var presenter: LoginPresenter
    lateinit var subscription: Subscription

    override val username: Observable<String> by lazy { ui.usernameField.textChanges().map { it.toString() } }
    override val password: Observable<String> by lazy { ui.passwordField.textChanges().map { it.toString() } }
    override val loginClicks: Observable<Unit> by lazy { ui.loginButton.clicks() }

    private val _resolutionResult: SerializedRelay<Boolean, Boolean> = PublishRelay.create<Boolean>().toSerialized()
    override val resolutionResult: Observable<Boolean> = _resolutionResult.asObservable()

    override val loginButtonEnabled by lazy { ui(ui.loginButton.enabled()) }
    override val loginSuccessful by lazy { ui<Boolean> { snackBar("Login attempt ${if (it) "succeeded" else "failed"}") } }

    override val showLogin = ui<Unit> { throw IllegalStateException("Already showing login") }
    override val resolveStatus = ui<Status> { status -> status.startResolutionForResult(this, RC_SAVE) }

    companion object {
        private val RC_SAVE = 2

        fun intent(ctx: Context): Intent {
            return ctx.intentFor<LoginActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GradesApplication.graph.plus(LoginModule()).inject(this)

        ui.setContentView(this)
        subscription = presenter.bind(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SAVE -> _resolutionResult.call(resultCode == RESULT_OK)
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}
