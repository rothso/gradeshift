package io.gradeshift.data.network.auth

import okhttp3.Interceptor
import okhttp3.Response
import rx.Observable
import rx.lang.kotlin.subscribeWith

class AuthenticationInterceptor(
        private val sessionHandler: SessionHandler,
        private val authenticator: Authenticator
) : Interceptor {

    companion object {
        const val HEADER_NEEDS_AUTHORIZED = "Needs-Authorized"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request.header(HEADER_NEEDS_AUTHORIZED)?.let {
            fun Token.isExpired(): Boolean = true // TODO
            sessionHandler.getToken().flatMap { token ->
                if (token.isExpired()) sessionHandler.renewTokenAndGet()
                else Observable.just(token)
            }.subscribeWith {
                onNext { token -> authenticator.authorize(request, token) }
                onError { throw it } // fatal: no user, no credentials, etc.
            }
        }

        request = request.newBuilder().removeHeader(HEADER_NEEDS_AUTHORIZED).build()
        return chain.proceed(request)
    }
}