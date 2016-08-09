package io.gradeshift.ui.overview

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.common.api.Status
import com.jakewharton.rxbinding.support.v4.widget.refreshes
import com.jakewharton.rxrelay.PublishRelay
import io.gradeshift.R
import io.gradeshift.domain.model.Course
import io.gradeshift.ui.common.ItemPressListener
import io.gradeshift.ui.common.ext.ui
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import rx.Observable
import javax.inject.Inject
import javax.inject.Provider

class OverviewUI @Inject constructor(
        private val adapterProvider: Provider<OverviewAdapter>, // TODO dont inject this
        private val navigator: Navigator
) : AnkoComponent<OverviewActivity>, OverviewPresenter.View, ItemPressListener {
    private lateinit var overviewAdapter: OverviewAdapter
    private lateinit var refreshView: SwipeRefreshLayout

    override val itemClicks: PublishRelay<Int> = PublishRelay.create()
    override val refreshes: Observable<Unit> by lazy { refreshView.refreshes() }
    override val showCourseDetail = ui<Pair<Int, List<Course>>> { pair ->
        navigator.showCourse(pair.first, pair.second)
    }
    override val showCourses = ui<List<Course>> { overviewAdapter.courses = it }
    override val loading = ui<Boolean> { refreshView.isRefreshing = it }

    lateinit private var activity: OverviewActivity
    override fun resolve(status: Status) {
        activity.startResolution(status)
    }
    override fun showLogin() {
        navigator.showLogin()
    }

    override fun createView(ui: AnkoContext<OverviewActivity>) = with(ui) {
        activity = owner
        overviewAdapter = adapterProvider.get()

        swipeRefreshLayout {
            this@OverviewUI.refreshView = this

            recyclerView {
                lparams(width = matchParent, height = matchParent)
                id = R.id.grades_overview_list
                layoutManager = LinearLayoutManager(ctx)
                itemAnimator = DefaultItemAnimator()
                adapter = overviewAdapter
                setHasFixedSize(true) // All views are the same height and width
            }
        }
    }

    override fun onItemPress(position: Int) {
        return itemClicks.call(position)
    }
}
