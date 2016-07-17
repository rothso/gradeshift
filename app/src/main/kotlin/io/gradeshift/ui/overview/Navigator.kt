package io.gradeshift.ui.overview

import android.content.Context
import io.gradeshift.ui.period.PeriodActivity

class Navigator(val context: Context) {

    fun showClass(classId: Int) {
        context.startActivity(PeriodActivity.intent(context, classId))
    }
}