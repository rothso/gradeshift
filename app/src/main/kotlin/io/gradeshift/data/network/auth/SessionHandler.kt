package io.gradeshift.data.network.auth

import com.fernandocejas.frodo.annotation.RxLogObservable
import io.gradeshift.data.network.api.LoginApi
import org.threeten.bp.Instant
import rx.Observable
import rx.annotations.Experimental

@Experimental
class SessionHandler(
        private val sessionStore: SessionStore,
        private val loginApi: LoginApi,
        private val smartLock: SmartLock
) {

    @RxLogObservable
    fun getToken(): Observable<Token> {
        fun isExpired(token: Token): Boolean = Instant.now().isAfter(token.expiry)

        return sessionStore.getToken()
                .flatMap { token -> if (isExpired(token)) getRenewedToken() else Observable.just(token) }
                .switchIfEmpty(getRenewedToken())
    }

    @RxLogObservable
    private fun getRenewedToken(): Observable<Token> {
        return smartLock.getCredentials()
                .flatMap { credential -> loginApi.login(credential.id, credential.password!!) }
                .doOnNext { token -> sessionStore.saveToken(token) }
    }
}
