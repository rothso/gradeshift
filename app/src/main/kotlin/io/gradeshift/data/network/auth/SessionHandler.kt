package io.gradeshift.data.network.auth

import com.fernandocejas.frodo.annotation.RxLogObservable
import io.gradeshift.data.network.api.LoginApi
import io.gradeshift.data.network.auth.exception.LoginFailedException
import org.threeten.bp.Instant
import rx.Observable
import rx.annotations.Experimental

@Experimental
class SessionHandler(
        private val sessionStore: SessionStore,
        private val credentialStore: CredentialStore,
        private val loginApi: LoginApi
) {

    @RxLogObservable
    fun getToken(): Observable<Token> {
        fun isExpired(token: Token): Boolean = Instant.now().isAfter(token.expiry)

        return sessionStore.getToken()
                .filter { !isExpired(it) }
                .switchIfEmpty(getRenewedToken())
    }

    @RxLogObservable
    private fun getRenewedToken(): Observable<Token> {
        return credentialStore.getCredentials()
                .flatMap { credentials -> loginApi.login(credentials) }
                .map { user -> user?.token ?: throw LoginFailedException() }
                .doOnNext { token -> sessionStore.saveToken(token) } // TODO is side-effect
    }
}
