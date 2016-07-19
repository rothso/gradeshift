package io.gradeshift.ui.overview

import android.graphics.Typeface
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jakewharton.rxrelay.PublishRelay
import io.gradeshift.R
import io.gradeshift.model.Class
import io.gradeshift.ui.NavigationHeaderUI
import io.gradeshift.ui.ext.ItemPressListener
import io.gradeshift.ui.ext.dimenAttr
import io.gradeshift.ui.ext.ui
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.drawerLayout
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

    lateinit var drawer: DrawerLayout
    lateinit var toolbar: Toolbar

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

        drawer = drawerLayout {
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = false

            // TODO extract when we may also need to use drawerLayout elsewhere
            coordinatorLayout {
                fitsSystemWindows = true

                appBarLayout(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                    fitsSystemWindows = false

                    toolbar = toolbar {
                        popupTheme = R.style.ThemeOverlay_AppCompat_Light
                    }.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize))

                }.lparams(width = matchParent, height = wrapContent)

                // TODO maybe extract to allow hotswapping different marking periods
                refreshView = swipeRefreshLayout {
                    post { isRefreshing = true } // Awaiting the initial data fetch
                    onRefresh {
                        refreshes.call(null)
                        isRefreshing = true
                    }

                    recyclerView {
                        id = R.id.grades_overview_list
                        layoutManager = LinearLayoutManager(ctx)
                        itemAnimator = DefaultItemAnimator()
                        adapter = overviewAdapter
                        setHasFixedSize(true) // All views are the same height and width
                    }.lparams(width = matchParent, height = matchParent)

                }.lparams(width = matchParent, height = matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }

                // TODO error view
            }

            navigationView {
                fitsSystemWindows = true
                val headerContext = AnkoContext.create(ctx, this)
                val headerView = NavigationHeaderUI()
                        .createView(headerContext)
                        .lparams(width = matchParent, height = dip(192)) // TODO R.dimen.nav_header_height
                addHeaderView(headerView)
                inflateMenu(R.menu.drawer_view)
                setNavigationItemSelectedListener {
                    Snackbar.make(this, it.title, Snackbar.LENGTH_SHORT).show()
                    this@drawerLayout.closeDrawers()
                    true
                }
            }.lparams(height = matchParent, width = wrapContent, gravity = Gravity.START)
        }

        drawer // Manual return because we can't return an assignment statement
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
