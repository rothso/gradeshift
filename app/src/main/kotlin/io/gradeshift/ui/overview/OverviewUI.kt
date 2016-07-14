package io.gradeshift.ui.overview

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.textView

class OverviewUI : AnkoComponent<OverviewActivity> {

    override fun createView(ui: AnkoContext<OverviewActivity>) = with(ui) {
        // FIXME toolbar implicitly appears
        coordinatorLayout {
            textView("Hello from Overview!")
        }
    }
}