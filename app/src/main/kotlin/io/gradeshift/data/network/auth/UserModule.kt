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

    @Provides @UserScope @Authenticated
    fun provideClient(baseClient: OkHttpClient,
                      @Authenticated sharedPreferences: SharedPreferences,
                      loginApi: LoginApi,
                      authDecorator: AuthInterceptor.Decorator): OkHttpClient {
        val sessionStore = SessionStore(sharedPreferences)
        val credentialStore = CredentialStore.DummyImpl(user)
        val sessionHandler = SessionHandler(sessionStore, credentialStore, loginApi)
        val authInterceptor = AuthInterceptor(sessionHandler, authDecorator)

        return baseClient.newBuilder().addInterceptor(authInterceptor).build()
    }
}