package io.gradeshift.ui.common.drawer

import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import io.gradeshift.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.support.v4.drawerLayout

class DrawerUI : AnkoComponent<DrawerActivity> {
    lateinit var drawer: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var navigationView: NavigationView

    override fun createView(ui: AnkoContext<DrawerActivity>): View = with(ui) {
        drawer = drawerLayout {
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = false // Otherwise it sets a padding on /itself/.

            // Inflate and add the app bar, which also contains our content_frame placeholder
            with(ContentUI()) {
                val context: AnkoContext<DrawerLayout> = AnkoContext.create(ctx, this@drawerLayout)
                addView(createView(context).lparams(width = matchParent, height = matchParent))
                this@DrawerUI.toolbar = toolbar
            }

            // Inside the drawer
            navigationView = navigationView {
                fitsSystemWindows = true
                addHeaderView(DrawerHeaderUI()
                        .createView(AnkoContext.create(ctx, this))
                        .lparams(width = matchParent, height = dimen(R.dimen.nav_header_height)))
                inflateMenu(R.menu.activity_temp_drawer)
            }.lparams(width = wrapContent, height = matchParent, gravity = Gravity.START)
        }

        drawer // manual return
    }
}