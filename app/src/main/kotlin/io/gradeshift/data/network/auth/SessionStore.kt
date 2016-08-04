package io.gradeshift.data.network.auth

import rx.Observable

interface SessionStore {
    fun getToken(user: User): Observable<Token>
    fun saveToken(user: User, token: Token)
}