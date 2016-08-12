package io.gradeshift.data.network.api

import io.gradeshift.data.network.auth.User
import io.gradeshift.domain.model.Credentials
import rx.Observable

interface LoginApi {
    fun login(credential: Credentials): Observable<User?>
}