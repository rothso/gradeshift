package io.gradeshift.data.network.auth

import io.gradeshift.domain.model.Credentials
import rx.Observable

interface CredentialStore {

    fun getCredentials(): Observable<Credentials>

    class DummyImpl(private val user: User) : CredentialStore {

        override fun getCredentials(): Observable<Credentials> {
            // TODO in an actual impl, lookup by id
            return Observable.just(Credentials("${user.id} user", "${user.id} password"))
        }
    }

}