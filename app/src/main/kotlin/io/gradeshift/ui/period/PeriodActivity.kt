package io.gradeshift.ui.period

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.gradeshift.GradesApplication
import io.gradeshift.R
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import rx.Subscription
import javax.inject.Inject

class PeriodActivity : AppCompatActivity() {
    @Inject lateinit var ui: PeriodUI
    @Inject lateinit var presenter: PeriodPresenter
    lateinit var subscription: Subscription

    companion object {
        const val EXTRA_CLASS_ID: String = "CLASS_ID"

        fun intent(context: Context, classId: Int): Intent {
            return context.intentFor<PeriodActivity>(EXTRA_CLASS_ID to classId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val classId = intent.getIntExtra(EXTRA_CLASS_ID, -1)

        GradesApplication.graph.plus(PeriodModule(classId)).inject(this)
        ui.setContentView(this)
        subscription = presenter.bind(ui)
    }

    @SuppressWarnings("PrivateResource")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}
