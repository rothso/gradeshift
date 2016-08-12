package io.gradeshift

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.NetworkModule
import io.gradeshift.data.network.api.LoginApi
import io.gradeshift.data.network.auth.AuthenticatorImpl
import io.gradeshift.domain.service.Authenticator
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Singleton

@Module(includes = arrayOf(NetworkModule::class))
class ApplicationModule(private val application: Application) {

    @Provides @Singleton
    fun provideApplicationContext(): Context = application

    @Provides @Singleton
    fun providePrefs(): SharedPreferences {
        // TODO [1.x] user-specific shared prefs
        return application.defaultSharedPreferences
    }

    @Provides @Singleton
    fun provideAuthenticator(loginApi: LoginApi): Authenticator {
        return AuthenticatorImpl(loginApi)
    }
}