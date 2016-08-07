package io.gradeshift

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.common.api.GoogleApiClient
import dagger.Module
import dagger.Provides
import org.jetbrains.anko.defaultSharedPreferences
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides @Singleton
    fun provideApplicationContext(): Context = application

    @Provides @Singleton
    fun providePrefs(): SharedPreferences {
        // TODO [1.x] user-specific shared prefs
        return application.defaultSharedPreferences
    }

    @Provides @Singleton
    fun provideGoogleApiClient(): GoogleApiClient {
        return GoogleApiClient.Builder(application.applicationContext)
                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                    override fun onConnected(p0: Bundle?): Unit = Timber.d("Connected, args ${p0.toString()}")
                    override fun onConnectionSuspended(p0: Int): Unit = Timber.d("Suspended, args $p0")
                })
                .addOnConnectionFailedListener { Timber.e("Connection failed") }
                .addApi(Auth.CREDENTIALS_API) // SmartLock
                .build()
    }

    @Provides @Singleton
    fun provideCredentialRequest(@Named("Identity") accountType: String): CredentialRequest {
        return CredentialRequest.Builder()
                .setAccountTypes(accountType)
                .setPasswordLoginSupported(true)
                .build()
    }
}