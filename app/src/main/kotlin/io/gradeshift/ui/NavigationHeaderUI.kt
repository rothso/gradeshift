package io.gradeshift.ui

import android.graphics.drawable.ColorDrawable
import android.support.design.widget.NavigationView
import android.view.Gravity
import android.view.View
import io.gradeshift.R
import io.gradeshift.ui.ext.colorAttr
import io.gradeshift.ui.ext.setTextAppearanceCompat
import org.jetbrains.anko.*

class NavigationHeaderUI : AnkoComponent<NavigationView> {

    override fun createView(ui: AnkoContext<NavigationView>): View = with(ui) {
        verticalLayout(R.style.ThemeOverlay_AppCompat_Dark) {
            background = ColorDrawable(colorAttr(R.attr.colorPrimaryDark))
            padding = dip(16)
            gravity = Gravity.BOTTOM

            textView("Username") {
                setTextAppearanceCompat(R.style.TextAppearance_AppCompat_Body1)
            }.lparams(width = matchParent, height = wrapContent)
        }
    }

}