package io.gradeshift.domain

import io.gradeshift.data.network.auth.User
import io.gradeshift.domain.model.Credentials
import io.gradeshift.domain.service.Authenticator
import rx.Observable

class LoginInteractor(
        private val authenticator: Authenticator,
        private val callback: Authenticator.Callback
) {

    fun attemptLogin(credentials: Credentials): Observable<User?> {
        return authenticator.login(credentials)
                .doOnNext { user -> user?.let { callback.userLoggedIn(it) } }
    }
}