package io.gradeshift.ui.resolver

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import org.jetbrains.anko.excludeFromRecents
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class ResolverActivity : AppCompatActivity() {
    @Inject lateinit var gac: GoogleApiClient
    private var isResolving = false

    companion object {
        private const val REQUEST_RESOLVE_ERROR = 1001                // onActivityResult
        private const val STATE_RESOLVING_ERROR = "key_resolve_state" // onSaveInstanceState
        private const val TAG_ERROR_DIALOG = "error_dialog"           // Error dialog
        private const val EXTRA_RESULT = "extra_result"               // Intent

        fun intent(ctx: Context, result: ConnectionResult): Intent {
            return ctx.intentFor<ResolverActivity>(EXTRA_RESULT to result).excludeFromRecents()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val result = intent.getParcelableExtra<ConnectionResult>(EXTRA_RESULT)
        isResolving = savedInstanceState?.getBoolean(STATE_RESOLVING_ERROR) ?: false

        if (isResolving) {
            return // Already resolving error
        } else if (result.hasResolution()) {
            try {
                d { "Attempting to resolve error" }
                isResolving = true
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR)
            } catch (error: IntentSender.SendIntentException) {
                e { "Error with the resolution intent, retrying " }
                gac.connect()
            }
        } else {
            d { "Showing error dialog for code ${result.errorCode}" }
            isResolving = true
            ErrorDialogFragment.create(result.errorCode, REQUEST_RESOLVE_ERROR).show(
                    supportFragmentManager, TAG_ERROR_DIALOG)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_RESOLVING_ERROR, isResolving)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            isResolving = false
            if (resultCode == Activity.RESULT_OK) {
                if (!gac.isConnecting && !gac.isConnected) gac.connect()
            }
        }
    }

    fun onDialogDismissed() {
        isResolving = false
    }
}
