package io.gradeshift.ui.overview

import android.content.Context
import io.gradeshift.ui.period.PeriodFragment
import io.gradeshift.ui.quarter.QuarterActivity

class Navigator(val context: Context) {

    fun showClass(classId: Int) {
        context.startActivity(QuarterActivity.intent(context, classId, "Quarter 5"))
    }
}