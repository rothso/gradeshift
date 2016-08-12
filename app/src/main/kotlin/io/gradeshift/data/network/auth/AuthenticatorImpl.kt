package io.gradeshift.data.network.auth

import io.gradeshift.data.network.api.LoginApi
import io.gradeshift.domain.model.Credentials
import io.gradeshift.domain.service.Authenticator
import rx.Observable
import javax.inject.Singleton

@Singleton
class AuthenticatorImpl(private val loginApi: LoginApi) : Authenticator {

    override fun login(credentials: Credentials): Observable<User?> {
        return loginApi.login(credentials)
    }
}