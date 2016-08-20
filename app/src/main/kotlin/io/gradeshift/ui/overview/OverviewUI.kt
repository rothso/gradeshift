package io.gradeshift.ui.overview

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.gradeshift.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import javax.inject.Inject

class OverviewUI @Inject constructor() : AnkoComponent<OverviewActivity> {
    lateinit var refreshView: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView

    override fun createView(ui: AnkoContext<OverviewActivity>) = with(ui) {
        refreshView = swipeRefreshLayout {
            recyclerView = recyclerView {
                lparams(width = matchParent, height = matchParent)
                id = R.id.grades_overview_list
                layoutManager = LinearLayoutManager(ctx)
                itemAnimator = DefaultItemAnimator()
                setHasFixedSize(true) // All views are the same height and width
            }
        }
        refreshView
    }
}
