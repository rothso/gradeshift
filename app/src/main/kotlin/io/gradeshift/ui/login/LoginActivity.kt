package io.gradeshift.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.widget.textChanges
import io.gradeshift.GradesApplication
import io.gradeshift.ui.common.ext.ui
import io.gradeshift.ui.main.MainActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
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

    override val loginButtonEnabled by lazy { ui(ui.loginButton.enabled()) }
    override val loginSuccessful = ui<Boolean> {
        // TODO better success redirection?
        startActivity(MainActivity.intent(this))
    }

    companion object {
        fun intent(ctx: Context): Intent {
            return ctx.intentFor<LoginActivity>().newTask().clearTask()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GradesApplication.graph.plus(LoginModule()).inject(this)

        ui.setContentView(this)
        subscription = presenter.bind(this)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}
