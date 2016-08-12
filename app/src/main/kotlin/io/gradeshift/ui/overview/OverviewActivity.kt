package io.gradeshift.ui.overview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.gradeshift.GradesApplication
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.model.QuarterParcel
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
        private val EXTRA_CURRENT_QUARTER = "current_quarter"

        fun intent(context: Context, quarter: Quarter): Intent {
            return context.intentFor<OverviewActivity>(EXTRA_CURRENT_QUARTER to QuarterParcel(quarter))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val quarter = intent.getParcelableExtra<QuarterParcel>(EXTRA_CURRENT_QUARTER).data
        GradesApplication.userGraph.plus(OverviewModule(this, quarter)).inject(this)

        ui.setContentView(this)
        subscription = presenter.bind(ui)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}
