package io.gradeshift.data.network.google

import android.content.Context
import android.os.Bundle
import com.github.ajalt.timberkt.d
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED
import io.gradeshift.ui.resolver.ResolverActivity
import org.jetbrains.anko.newTask

class GoogleClient(private val context: Context) :
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    val gac: GoogleApiClient by lazy {
        GoogleApiClient.Builder(context, this, this)
                .addApi(Auth.CREDENTIALS_API) // SmartLock
                .build().apply { connect() }
    }

    override fun onConnected(connectionHint: Bundle?) {
        d { "Connected" }
    }

    override fun onConnectionSuspended(cause: Int) {
        when (cause) {
            CAUSE_NETWORK_LOST -> d { "Lost peer device connection" }
            CAUSE_SERVICE_DISCONNECTED -> d { "API service killed" }
        }
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        val intent = ResolverActivity.intent(context, result).newTask()
        context.startActivity(intent)
    }
}