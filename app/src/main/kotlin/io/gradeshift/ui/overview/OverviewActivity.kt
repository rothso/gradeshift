package io.gradeshift.ui.overview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding.support.v4.widget.refreshes
import com.jakewharton.rxbinding.widget.itemSelections
import com.jakewharton.rxrelay.PublishRelay
import io.gradeshift.GradesApplication
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.model.QuarterParcel
import io.gradeshift.ext.spread
import io.gradeshift.ui.common.ItemPressListener
import io.gradeshift.ui.common.ViewConsumer
import io.gradeshift.ui.common.drawer.DrawerActivity
import io.gradeshift.ui.common.ext.ui
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import rx.Observable
import rx.Subscription
import javax.inject.Inject

class OverviewActivity : DrawerActivity(), OverviewPresenter.View, ItemPressListener {
    @Inject lateinit var ui: OverviewUI
    @Inject lateinit var presenter: OverviewPresenter
    @Inject lateinit var navigator: Navigator
    @Inject lateinit var adapter: OverviewAdapter
    lateinit var subscription: Subscription

    // TODO extract into hotswappable ReportView
    override val itemClicks: PublishRelay<Int> = PublishRelay.create()
    override val showCourseDetail: ViewConsumer<Pair<Int, List<Course>>> = ui { it.spread(navigator::showCourse) }
    override val showCourses: ViewConsumer<List<Course>> = ui { adapter.courses = it }

    override val refreshes: Observable<Unit> by lazy { ui.refreshView.refreshes() }
    override val loading: ViewConsumer<Boolean> = ui { ui.refreshView.isRefreshing = it }

    override val quarterSelections: Observable<Int> by lazy { ui.spinner.itemSelections() }
    override val showQuarterOptions: ViewConsumer<List<Quarter>> = ui { ui.spinner.adapter = DropdownArrayAdapter<Quarter>(this, it) }

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
        ui.recyclerView.adapter = adapter
        subscription = presenter.bind(this)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }

    override fun onItemPress(position: Int) {
        itemClicks.call(position)
    }
}
