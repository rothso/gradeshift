package io.gradeshift.ui.quarter

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.textView

class QuarterUI(val classId: Int) : AnkoComponent<QuarterActivity> {

    override fun createView(ui: AnkoContext<QuarterActivity>) = with(ui) {
        relativeLayout {
            textView("Hello from QuarterUI: Class # $classId")
        }
    }
}