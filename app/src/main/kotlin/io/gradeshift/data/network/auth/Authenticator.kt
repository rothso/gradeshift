package io.gradeshift.data.network.auth

import okhttp3.Request

interface Authenticator {
    fun authorize(request: Request, token: Token): Request
}