package io.gradeshift.ui.main

import android.widget.Button
import io.gradeshift.R
import io.gradeshift.ui.common.ext.colorAttr
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout

class MainUI : AnkoComponent<MainActivity> {
    lateinit var overviewButton: Button

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        coordinatorLayout {
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = true

            relativeLayout {
                padding = dip(32)
                backgroundColor = colorAttr(R.attr.colorPrimary)

                overviewButton = button {
                    textResource = R.string.view_grades
                }.lparams { centerInParent() }
            }.lparams(width = matchParent, height = matchParent)
        }
    }

}
