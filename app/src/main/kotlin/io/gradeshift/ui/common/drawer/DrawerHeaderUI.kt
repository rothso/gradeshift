package io.gradeshift.ui.common.drawer

import android.support.design.widget.NavigationView
import android.view.Gravity
import io.gradeshift.R
import io.gradeshift.ui.common.ext.setTextAppearanceCompat
import org.jetbrains.anko.*

class DrawerHeaderUI : AnkoComponent<NavigationView> {

    override fun createView(ui: AnkoContext<NavigationView>) = with(ui) {
        verticalLayout(R.style.ThemeOverlay_AppCompat_Dark) {
            lparams(width = matchParent, height = dimen(R.dimen.nav_header_height))
            backgroundResource = R.drawable.side_nav_bar
            verticalPadding = dimen(R.dimen.activity_vertical_margin)
            horizontalPadding = dimen(R.dimen.activity_horizontal_margin)
            gravity = Gravity.BOTTOM

            imageView(android.R.drawable.sym_def_app_icon) {
                topPadding = dimen(R.dimen.nav_header_vertical_spacing)
            }.lparams(width = wrapContent, height = wrapContent)

            textView("Android Studio") {
                setTextAppearanceCompat(R.style.TextAppearance_AppCompat_Body1)
                topPadding = dimen(R.dimen.nav_header_vertical_spacing)
            }.lparams(width = matchParent, height = wrapContent)

            textView("android.studio@android.com") {
            }.lparams(width = wrapContent, height = wrapContent)
        }
    }
}