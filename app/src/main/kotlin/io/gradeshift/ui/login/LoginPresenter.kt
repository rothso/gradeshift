package io.gradeshift.ui.login

import com.github.ajalt.timberkt.d
import io.gradeshift.data.network.google.GacResolution
import io.gradeshift.data.network.google.SmartLock
import io.gradeshift.ui.common.base.Presenter
import io.gradeshift.ui.common.ext.bind
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription

class LoginPresenter(private val smartLock: SmartLock) : Presenter<LoginPresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        val username = view.username.share()
        val password = view.password.share()

        val credentials = Observable.combineLatest(username, password, { u, p -> Pair(u, p) }).share()

        subscription += credentials
                .map { it.first.isNotBlank() && it.second.isNotBlank() }
                .bind(view.loginButtonEnabled)

        subscription += view.loginClicks
                .withLatestFrom(credentials, { click, credential -> credential })
                .doOnNext { d { "Submitting credentials for ${it.first}" } }
                .switchMap { smartLock.saveCredentials(it.first, "Name", it.second) }
                .doOnNext { success -> d { "Login attempt ${if (success) "succeeded" else "failed"}" } }
                .compose { GacResolution.resolve(it, view) }
                .bind(view.loginSuccessful)

        // TODO hit the server before blindly saving credentials

        return subscription
    }

    interface View : GacResolution.Callback {
        val username: Observable<String>
        val password: Observable<String>
        val loginClicks: Observable<Unit>
        val resolutionResult: Observable<Boolean>

        val loginButtonEnabled: (Observable<Boolean>) -> Subscription
        val loginSuccessful: (Observable<Boolean>) -> Subscription
    }
}