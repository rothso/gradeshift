package io.gradeshift.data.network.google

import com.fernandocejas.frodo.annotation.RxLogObservable
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.w
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.common.api.CommonStatusCodes.*
import com.google.android.gms.common.api.GoogleApiClient
import io.gradeshift.data.network.auth.exception.LoginRequiredException
import io.gradeshift.data.network.auth.exception.ResolutionRequiredException
import io.gradeshift.data.util.PendingResultObservable
import rx.Observable

class SmartLock(
        private val gac: GoogleApiClient,
        private val identity: String
) {

    @RxLogObservable
    fun getCredentials(): Observable<Credential> {
        val request = Auth.CredentialsApi.request(gac, buildCredentialRequest())
        return PendingResultObservable.from(request).map { result ->
            when (result.status.statusCode) {
                SUCCESS or SUCCESS_CACHE -> {
                    d { "Credentials received for ${result.credential.id}" }
                    result.credential
                }
                RESOLUTION_REQUIRED -> {
                    d { "Multiple saved credentials, manual resolution required" }
                    throw ResolutionRequiredException(result.status)
                }
                SIGN_IN_REQUIRED -> {
                    d { "No saved credentials, sign-in required" }
                    throw LoginRequiredException()
                }
                else -> {
                    w { "Unrecognized status code ${result.status.statusCode}" }
                    throw LoginRequiredException() // unexpected errors
                }
            }
        }
    }

    @RxLogObservable
    fun saveCredentials(uniqueId: String, name: String, password: String): Observable<Boolean> {
        throw UnsupportedOperationException()
//        val credential = Credential.Builder(uniqueId)
//                .setAccountType(identity)
//                .setName(name)
//                .setPassword(password)
//                .build()
//
//        val save = Auth.CredentialsApi.save(gac, credential)
//        return PendingResultObservable.from(save).map { result ->
//            when (result.status.statusCode) {
//                SUCCESS or SUCCESS_CACHE -> true
//                else -> {
//                    e { "Status code ${result.status.statusCode}"}
//                    false
//                }
//            }
//        }
    }

    private fun buildCredentialRequest(): CredentialRequest {
        return CredentialRequest.Builder()
                .setAccountTypes(identity)
                .setPasswordLoginSupported(true)
                .build()
    }
}