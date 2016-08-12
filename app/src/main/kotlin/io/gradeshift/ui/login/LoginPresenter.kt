package io.gradeshift.ui.login

import com.github.ajalt.timberkt.d
import io.gradeshift.domain.LoginInteractor
import io.gradeshift.domain.model.Credentials
import io.gradeshift.ui.common.base.Presenter
import io.gradeshift.ui.common.ext.bind
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription

class LoginPresenter(private val interactor: LoginInteractor) : Presenter<LoginPresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        val username = view.username.share()
        val password = view.password.share()

        val credentials = Observable.combineLatest(
                username, password, { u, p -> Credentials(u, p) }).share()

        subscription += credentials
                .map { it.username.isNotBlank() && it.password.isNotBlank() }
                .bind(view.loginButtonEnabled)

        subscription += view.loginClicks
                .withLatestFrom(credentials, { click, credential -> credential })
                .doOnNext { d { "Logging in ${it.username}" } }
                .switchMap { credential -> interactor.attemptLogin(credential) }
                .map { user -> user != null }
                .doOnNext { success -> d { "Login ${if (success) "succeeded" else "failed"}" } }
                .bind(view.loginSuccessful)

        return subscription
    }

    interface View {
        val username: Observable<String>
        val password: Observable<String>
        val loginClicks: Observable<Unit>

        val loginButtonEnabled: (Observable<Boolean>) -> Subscription
        val loginSuccessful: (Observable<Boolean>) -> Subscription
    }
}