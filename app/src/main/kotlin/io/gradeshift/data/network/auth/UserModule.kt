package io.gradeshift.data.network.auth

import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.common.api.GoogleApiClient
import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.LoginApi
import okhttp3.OkHttpClient
import javax.inject.Named

@Module
class UserModule(private val user: User) {

    companion object {
        const val AUTHENTICATED = "Authenticated" // Qualifier
    }

    @Provides @UserScope
    fun provideUser(): User = user

    @Provides @UserScope
    fun provideSessionStore(): SessionStore {
        return object : SessionStore {
            override fun getToken(user: User) = throw UnsupportedOperationException()
            override fun saveToken(user: User, token: Token) = throw UnsupportedOperationException()
        }
    }

    @Provides @UserScope
    fun provideSessionHandler(currentUser: User, store: SessionStore, loginApi: LoginApi, @Named("Identity") identity: String,
    googleApiClient: GoogleApiClient, credentialRequest: CredentialRequest): SessionHandler {
        return SessionHandler(currentUser, store, loginApi, identity, googleApiClient, credentialRequest)
    }

    @Provides @UserScope
    fun provideInterceptor(sessionHandler: SessionHandler, authenticator: Authenticator): AuthenticationInterceptor {
        return AuthenticationInterceptor(sessionHandler, authenticator)
    }

    @Provides @UserScope @Named(AUTHENTICATED)
    fun provideClient(authInterceptor: AuthenticationInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }
}