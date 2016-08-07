package io.gradeshift.data.network.auth

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
        private val sessionHandler: SessionHandler,
        private val authDecorator: Decorator
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Assumes all requests using this client need authentication
        val token = sessionHandler.getToken().toBlocking().first()
        request = authDecorator.decorate(request, token)

        return chain.proceed(request)
    }

    interface Decorator {
        fun decorate(request: Request, token: Token): Request
    }
}