package io.gradeshift.ui.overview

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.common.api.Status
import io.gradeshift.GradesApplication
import io.gradeshift.domain.model.Quarter
import io.gradeshift.ui.common.drawer.DrawerActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import rx.Subscription
import javax.inject.Inject

class OverviewActivity : DrawerActivity() {
    @Inject lateinit var ui: OverviewUI
    @Inject lateinit var presenter: OverviewPresenter
    lateinit var subscription: Subscription

    companion object {
        private val RC_READ = 3
        private val CURRENT_QUARTER = Quarter.DUMMY_QUARTER // TODO pass with intent, or assume somehow

        fun intent(context: Context): Intent {
            return context.intentFor<OverviewActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GradesApplication.userGraph.plus(OverviewModule(this, CURRENT_QUARTER)).inject(this)

        ui.setContentView(this)
        subscription = presenter.bind(ui)
    }

    fun startResolution(status: Status) {
        try {
            status.startResolutionForResult(this, RC_READ)
        } catch (error: IntentSender.SendIntentException) {
            e { "Failed to send intent" }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            RC_READ -> {
                if (resultCode == RESULT_OK) {
                    val credential = data.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                    d { "Got credential: ${credential.id}"}
                } else {
                    e { "Credential Read: NOT OK" }
                }
            }
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}
