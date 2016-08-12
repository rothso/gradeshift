package io.gradeshift.data.network.provider.focus.converter

import com.github.ajalt.timberkt.d
import io.gradeshift.data.network.api.LoginApi
import io.gradeshift.data.network.auth.User
import io.gradeshift.domain.model.Credentials
import rx.Observable

class FocusLoginApi : LoginApi {

    override fun login(credential: Credentials): Observable<User?> {
        // TODO hook to REST endpoint
        return Observable.just(User.DUMMY_USER)
                .doOnNext { d { "Returning dummy user" } }
    }
}