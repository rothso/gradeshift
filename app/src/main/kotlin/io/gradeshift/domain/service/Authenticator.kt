package io.gradeshift.domain.service

import io.gradeshift.data.network.auth.User
import io.gradeshift.domain.model.Credentials
import rx.Observable

// TODO find a more meaningful name
interface Authenticator {

    interface Callback {
        fun userLoggedIn(user: User)
    }

    fun login(credentials: Credentials): Observable<User?>
}