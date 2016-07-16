package io.gradeshift.ui.overview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.gradeshift.GradesApplication
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.singleTop
import rx.Subscription
import javax.inject.Inject

class OverviewActivity : AppCompatActivity() {
    @Inject lateinit var ui: OverviewUI
    @Inject lateinit var presenter: OverviewPresenter
    lateinit var subscription: Subscription

    companion object {
        fun intent(context: Context): Intent {
            return context.intentFor<OverviewActivity>().singleTop()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GradesApplication.graph.plus(OverviewModule(this)).inject(this)
        ui.setContentView(this)
        subscription = presenter.bind(ui)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}
