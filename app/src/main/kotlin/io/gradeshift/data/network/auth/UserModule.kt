package io.gradeshift.data.network.auth

import android.content.SharedPreferences
import com.google.android.gms.common.api.GoogleApiClient
import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.LoginApi
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Provider

@Module
class UserModule(private val user: User) {

    @Provides @UserScope
    fun provideUser(): User = user

    @Provides @UserScope
    fun provideSmartLock(googleApiClient: Provider<GoogleApiClient>, @Named("Identity") identity: String): SmartLock {
        return SmartLock(googleApiClient, identity)
    }

    @Provides @UserScope
    fun provideSessionStore(@Authenticated sharedPreferences: SharedPreferences): SessionStore {
        return SessionStore(sharedPreferences)
    }

    @Provides @UserScope
    fun provideSessionHandler(store: SessionStore, loginApi: LoginApi, smartLock: SmartLock): SessionHandler {
        return SessionHandler(store, loginApi, smartLock)
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