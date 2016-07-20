package io.gradeshift.ui.main

import io.gradeshift.R
import io.gradeshift.ui.common.ext.colorAttr
import io.gradeshift.ui.overview.OverviewActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout

class MainUI : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        coordinatorLayout {
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = true

            relativeLayout {
                padding = dip(32)
                backgroundColor = colorAttr(R.attr.colorPrimary)

                button {
                    textResource = R.string.view_grades
                    onClick { ctx.startActivity(OverviewActivity.intent(ctx)) }
                }.lparams { centerInParent() }
            }.lparams(width = matchParent, height = matchParent)
        }
    }

}
