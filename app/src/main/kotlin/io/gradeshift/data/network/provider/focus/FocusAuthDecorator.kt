package io.gradeshift.data.network.provider.focus

import io.gradeshift.data.network.auth.AuthInterceptor
import io.gradeshift.data.network.auth.Token
import okhttp3.Request

class FocusAuthDecorator : AuthInterceptor.Decorator {

    override fun decorate(request: Request, token: Token): Request {
        return request.newBuilder()
                .addHeader("PHPSESSID", token.value)
                .addHeader("session_timeout", token.expiry.epochSecond.toString())
                .build()
    }
}
