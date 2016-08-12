package io.gradeshift.data.network.auth

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.LoginApi
import okhttp3.OkHttpClient

@Module
class UserModule(private val user: User) {

    @Provides @UserScope
    fun provideUser(): User = user

    @Provides @UserScope
    fun provideSessionStore(@Authenticated sharedPreferences: SharedPreferences): SessionStore {
        return SessionStore(sharedPreferences)
    }

    @Provides @UserScope
    fun provideCredentialStore(user: User): CredentialStore{
        return CredentialStore.DummyImpl(user)
    }

    @Provides @UserScope
    fun provideSessionHandler(sessStore: SessionStore, credStore: CredentialStore, loginApi: LoginApi): SessionHandler {
        return SessionHandler(sessStore, credStore, loginApi)
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