package io.gradeshift.data.network.google

import android.content.Context
import android.os.Bundle
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GoogleModule {

    // TODO @ActivityScope
    @Provides @Singleton
    fun provideGoogleApiClient(context: Context): GoogleApiClient {
        return GoogleApiClient.Builder(context)
                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                    override fun onConnected(p0: Bundle?): Unit = d { "Connected, args ${p0.toString()}" }
                    override fun onConnectionSuspended(p0: Int): Unit = d { "Suspended, args $p0" }
                })
                .addOnConnectionFailedListener { e { "Connection failed" } }
                .addApi(Auth.CREDENTIALS_API) // SmartLock
                .build().apply { connect() }
    }
}