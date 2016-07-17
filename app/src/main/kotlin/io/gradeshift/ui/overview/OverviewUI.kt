package io.gradeshift.ui.overview

import android.graphics.Typeface
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jakewharton.rxrelay.PublishRelay
import io.gradeshift.R
import io.gradeshift.model.Class
import io.gradeshift.ui.ext.ItemPressListener
import io.gradeshift.ui.ext.ui
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import javax.inject.Inject
import javax.inject.Provider

class OverviewUI @Inject constructor(
        val adapterProvider: Provider<OverviewAdapter>,
        val navigator: Navigator
) : AnkoComponent<OverviewActivity>, OverviewPresenter.View, ItemPressListener {
    lateinit var overviewAdapter: OverviewAdapter
    lateinit var refreshView: SwipeRefreshLayout

    override val itemClicks: PublishRelay<Int> = PublishRelay.create()
    override val refreshes: PublishRelay<Void> = PublishRelay.create()
    override val showClassDetail = ui<Class> { navigator.showClass(it.id) }
    override val showClasses = ui<List<Class>> {
        refreshView.isRefreshing = false
        overviewAdapter.classes = it
    }

    override fun onItemPress(position: Int) = itemClicks.call(position)

    override fun createView(ui: AnkoContext<OverviewActivity>) = with(ui) {
        overviewAdapter = adapterProvider.get()

        frameLayout() {
            lparams(width = matchParent, height = matchParent)

            refreshView = swipeRefreshLayout {
                lparams(width = matchParent, height = matchParent)
                post { isRefreshing = true } // Awaiting the initial data fetch
                onRefresh {
                    refreshes.call(null)
                    isRefreshing = true
                }

                recyclerView {
                    id = R.id.grades_overview_list
                    lparams(width = matchParent, height = matchParent)
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = overviewAdapter
                    setHasFixedSize(true) // All views are the same height and width
                }
            }
            // TODO error view
        }
    }

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
