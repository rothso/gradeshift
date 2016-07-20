package io.gradeshift.ui.quarter

import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.LayoutParams.*
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import io.gradeshift.R
import io.gradeshift.ui.common.ext.colorAttr
import io.gradeshift.ui.common.ext.ctlparams
import io.gradeshift.ui.common.ext.dimenAttr
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.viewPager

class QuarterUI : AnkoComponent<QuarterActivity> {

    lateinit var toolbar: Toolbar
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun createView(ui: AnkoContext<QuarterActivity>) = with(ui) {
        coordinatorLayout {
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = true

            appBarLayout(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                setExpanded(false) // Start with collapsible content hidden
                fitsSystemWindows = true

                collapsingToolbarLayout {
                    isTitleEnabled = false

                    // TODO fade out toolbar title on expand
                    toolbar = toolbar {
                        gravity = Gravity.TOP
                        popupTheme = R.style.ThemeOverlay_AppCompat_Light
                        onClick { this@appBarLayout.setExpanded(true) }
                    }.ctlparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize)) {
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                    }

                }.lparams(width = matchParent, height = dip(200)) {
                    scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or SCROLL_FLAG_SNAP
                }

                collapsingToolbarLayout {
                    tabLayout = tabLayout {
                        tabMode = TabLayout.MODE_SCROLLABLE
                        setTabTextColors(Color.WHITE.withAlpha(255/2), Color.WHITE)
                        setSelectedTabIndicatorColor(colorAttr(R.attr.colorAccent))
                        leftPadding = dip(60)
                        clipToPadding = false
                    }.ctlparams(width = matchParent, height = wrapContent) {
                        gravity = Gravity.BOTTOM
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
                }

            }.lparams(width = matchParent)

            viewPager = viewPager {
                id = View.generateViewId()
            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior() // appbar_scrolling_view_behavior
            }
        }
    }
}