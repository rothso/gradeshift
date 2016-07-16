package io.gradeshift.ui.main

import android.support.v4.content.ContextCompat
import io.gradeshift.R
import io.gradeshift.ui.overview.OverviewActivity
import org.jetbrains.anko.*

class MainUI : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            padding = dip(32)
            backgroundColor = ContextCompat.getColor(ctx, R.color.colorPrimary)

            button {
                textResource = R.string.view_grades
                onClick { ctx.startActivity(OverviewActivity.intent(ctx))}
            }.lparams { centerInParent() }
        }
    }

}
