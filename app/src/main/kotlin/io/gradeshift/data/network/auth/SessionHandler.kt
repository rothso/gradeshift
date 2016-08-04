package io.gradeshift.data.network.auth

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.common.api.CommonStatusCodes.*
import com.google.android.gms.common.api.GoogleApiClient
import io.gradeshift.data.network.api.LoginApi
import io.gradeshift.data.network.auth.exception.LoginRequiredException
import io.gradeshift.data.network.auth.exception.ResolutionRequiredException
import io.gradeshift.data.util.PendingResultObservable
import rx.Observable
import timber.log.Timber

class SessionHandler(
        private val currentUser: User,
        private val sessionStore: SessionStore,
        private val loginApi: LoginApi,
        private val identity: String,
        private val googleApiClient: GoogleApiClient,
        private val credentialRequest: CredentialRequest
) {

    fun getToken(): Observable<Token> = sessionStore.getToken(currentUser)

    fun renewTokenAndGet(): Observable<Token> {
        return PendingResultObservable.from(Auth.CredentialsApi.request(googleApiClient, credentialRequest))
                .flatMap { res ->
                    when (res.status.statusCode) {
                        SUCCESS or SUCCESS_CACHE -> {
                            // Verify account belongs to the service provider
                            val accountType = res.credential.accountType
                            if (accountType != identity) {
                                Timber.d("Received account type $accountType, wanted $identity, falling back to login")
                                throw LoginRequiredException()
                            }

                            Timber.d("Success, automatically signing in ${res.credential.id}")
                            loginApi.login(res.credential.id, res.credential.password!!)
                        }
                        RESOLUTION_REQUIRED -> {
                            Timber.d("Multiple saved credentials, manual resolution required")
                            throw ResolutionRequiredException()
                        }
                        SIGN_IN_REQUIRED -> {
                            Timber.d("No saved credentials, sign-in required")
                            throw LoginRequiredException()
                        }
                        else -> {
                            Timber.d("Unrecognized status code ${res.status.statusCode}, falling back to login")
                            throw LoginRequiredException() // unaccounted errors, fall back to login
                        }
                    }
                }
                .doOnNext { token ->
                    Timber.d("Successfully retrieved new token, saving")
                    sessionStore.saveToken(currentUser, token)
                }
    }
}
