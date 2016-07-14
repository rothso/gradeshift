package io.gradeshift.ui.main

import android.support.v4.content.ContextCompat
import io.gradeshift.R
import org.jetbrains.anko.*

class MainUI : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            padding = dip(32)
            backgroundColor = ContextCompat.getColor(ctx, R.color.colorPrimary)

            button {
                textResource = R.string.view_grades
                onClick { } // TODO start io.gradeshift.ui.overview activity
            }.lparams { centerInParent() }
        }
    }

}
