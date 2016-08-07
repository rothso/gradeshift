package io.gradeshift.data.network.auth

import android.content.SharedPreferences
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.common.api.GoogleApiClient
import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.LoginApi
import okhttp3.OkHttpClient
import javax.inject.Named

@Module
class UserModule(private val user: User) {

    @Provides @UserScope
    fun provideUser(): User = user

    @Provides @UserScope
    fun provideSessionStore(@Authenticated sharedPreferences: SharedPreferences): SessionStore {
        return SessionStore(sharedPreferences)
    }

    @Provides @UserScope
    fun provideSessionHandler(store: SessionStore, loginApi: LoginApi, @Named("Identity") identity: String,
                              googleApiClient: GoogleApiClient, credentialRequest: CredentialRequest): SessionHandler {
        return SessionHandler(store, loginApi, identity, googleApiClient, credentialRequest)
    }

    @Provides @UserScope
    fun provideInterceptor(sessionHandler: SessionHandler, authDecorator: AuthInterceptor.Decorator): AuthInterceptor {
        return AuthInterceptor(sessionHandler, authDecorator)
    }

    @Provides @UserScope @Authenticated
    fun provideClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }
}