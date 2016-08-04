package io.gradeshift.data.network.api

import io.gradeshift.data.network.auth.Token
import rx.Observable

interface LoginApi {
    fun login(username: String, password: String): Observable<Token>
}