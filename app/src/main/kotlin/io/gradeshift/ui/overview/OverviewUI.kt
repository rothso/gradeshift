package io.gradeshift.ui.overview

import android.os.Build
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewOutlineProvider
import io.gradeshift.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import javax.inject.Inject

class OverviewUI @Inject constructor() : AnkoComponent<OverviewActivity> {
    lateinit var refreshView: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView

    override fun createView(ui: AnkoContext<OverviewActivity>) = with(ui) {
        refreshView = swipeRefreshLayout {

            linearLayout {
                lparams(width = matchParent, height = matchParent)
                padding = dip(10)
                clipToPadding = false

                // TODO hide until populated with data
                recyclerView = recyclerView {
                    id = R.id.grades_overview_list
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        padding = dip(5)
                        elevation = 2f
                        outlineProvider = ViewOutlineProvider.BOUNDS
                    }
                    layoutManager = LinearLayoutManager(ctx)
                    itemAnimator = DefaultItemAnimator()
                    setHasFixedSize(true) // All views are the same height and width
                }.lparams(width = matchParent, height = wrapContent)
            }
        }

        refreshView
    }
}
