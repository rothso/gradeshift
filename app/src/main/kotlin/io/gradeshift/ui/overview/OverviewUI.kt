package io.gradeshift.ui.overview

import android.graphics.Typeface
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jakewharton.rxrelay.PublishRelay
import io.gradeshift.R
import io.gradeshift.domain.model.Course
import io.gradeshift.ui.common.ext.ItemPressListener
import io.gradeshift.ui.common.ext.ui
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import javax.inject.Inject
import javax.inject.Provider

class OverviewUI @Inject constructor(
        private val adapterProvider: Provider<OverviewAdapter>,
        private val navigator: Navigator
) : AnkoComponent<OverviewActivity>, OverviewPresenter.View, ItemPressListener {
    private lateinit var overviewAdapter: OverviewAdapter
    private lateinit var refreshView: SwipeRefreshLayout

    override val itemClicks: PublishRelay<Int> = PublishRelay.create()
    override val refreshes: PublishRelay<Unit> = PublishRelay.create()
    override val showCourseDetail = ui<Pair<Int, List<Course>>> { pair ->
        navigator.showCourse(pair.first, pair.second)
    }
    override val showCourses = ui<List<Course>> { overviewAdapter.courses = it }
    override val loading = ui<Boolean> { refreshView.isRefreshing = it }

    override fun createView(ui: AnkoContext<OverviewActivity>) = with(ui) {
        overviewAdapter = adapterProvider.get()

        swipeRefreshLayout {
            this@OverviewUI.refreshView = this
            onRefresh { refreshes.call(Unit) }

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

    // TODO move inside Adapter, clean up
    class Item : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = dip(48))
                orientation = LinearLayout.HORIZONTAL
                horizontalPadding = dip(16)

                textView {
                    lparams {
                        gravity = Gravity.CENTER_VERTICAL
                        weight = 1.0f
                    }
                    id = R.id.grades_overview_class_name
                    singleLine = true
                    ellipsize = TextUtils.TruncateAt.END
                    textSize = 16f
                }

                textView {
                    lparams(width = dip(40)) {
                        gravity = Gravity.CENTER_VERTICAL
                        marginStart = dip(8)
                    }
                    id = R.id.grades_overview_class_score
                    textSize = 16f
                    typeface = Typeface.create(typeface, Typeface.BOLD)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }
        }
    }
}
