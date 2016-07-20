package io.gradeshift.ui.common.drawer

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.os.Build
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.View
import io.gradeshift.R
import io.gradeshift.ui.common.ext.dimenAttr
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout

class ContentUI : AnkoComponent<DrawerLayout> {
    lateinit var toolbar: Toolbar

    companion object {
        val CONTENT_FRAME_ID = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<DrawerLayout>) = with(ui) {
        coordinatorLayout {
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = true

            appBarLayout(R.style.AppTheme_AppBarOverlay) {
                // StateListAnimator overrides elevation: http://stackoverflow.com/a/37992366/5623874
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    stateListAnimator = StateListAnimator()
                    stateListAnimator.addState(IntArray(0), ObjectAnimator.ofFloat(this, "elevation", 0f))
                }

                toolbar = toolbar {
                    popupTheme = R.style.AppTheme_PopupOverlay
                }.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize)) {
                    scrollFlags = 0 // Prevent coordinatorLayout from scrolling toolbar offscreen
                }
            }.lparams(width = matchParent, height = wrapContent)

            // ConstraintLayout not yet supported: https://github.com/Kotlin/anko/issues/210
            relativeLayout {
                id = CONTENT_FRAME_ID
            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }
}
