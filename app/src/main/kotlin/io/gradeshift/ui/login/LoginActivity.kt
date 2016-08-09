package io.gradeshift.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.jakewharton.rxbinding.view.clicks
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.gradeshift.GradesApplication
import io.gradeshift.data.network.google.SmartLock
import io.gradeshift.ui.common.ActivityScope
import io.gradeshift.ui.common.base.Presenter
import org.jetbrains.anko.*
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.plusAssign
import rx.lang.kotlin.subscribeWith
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginPresenter.View {
    @Inject lateinit var ui: LoginUI
    @Inject lateinit var presenter: LoginPresenter
    lateinit var subscription: Subscription

    companion object {
        fun intent(ctx: Context): Intent {
            return ctx.intentFor<LoginActivity>()
        }
    }

    override val submits: Observable<Pair<String, String>> by lazy {
        // TODO validation
        ui.loginButton.clicks().map {
            Pair(ui.usernameField.text.toString(), ui.passwordField.text.toString())
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

@ActivityScope
@Subcomponent(modules = arrayOf(LoginModule::class))
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}

@Module
class LoginModule {

    @Provides @ActivityScope
    fun provideUI(): LoginUI = LoginUI()

    @Provides @ActivityScope
    fun providePresenter(smartLock: SmartLock): LoginPresenter = LoginPresenter(smartLock)
}

class LoginPresenter(private val smartLock: SmartLock) : Presenter<LoginPresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        subscription += view.submits
                .doOnNext { d { "Submitting credentials for ${it.first}" } }
                .flatMap { smartLock.saveCredentials(it.first, "Name", it.second) }
                .subscribeWith {
                    onNext { success -> d { "Login attempt ${if (success) "succeeded" else "failed"}" } }
                    onError { e(it) { "Catastrophe while logging in" } }
                }

        return subscription
    }

    interface View {
        val submits: Observable<Pair<String, String>>
    }
}

class LoginUI : AnkoComponent<LoginActivity> {

    lateinit var loginButton: Button
    lateinit var usernameField: EditText
    lateinit var passwordField: EditText

    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {
        verticalLayout {
            padding = dip(30)
            usernameField = editText {
                hint = "Name"
                textSize = 24f
            }
            passwordField = editText {
                hint = "Password"
                textSize = 24f
            }
            loginButton = button("Login") {
                textSize = 26f
            }
        }
    }
}