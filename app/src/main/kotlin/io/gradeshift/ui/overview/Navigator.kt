package io.gradeshift.ui.overview

import android.content.Context
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Quarter
import io.gradeshift.ui.quarter.QuarterActivity

class Navigator(
        private val context: Context,
        private val currentQuarter: Quarter
) {

    fun showCourse(selectedCourseIndex: Int, courses: List<Course>) {
        val intent = QuarterActivity.intent(context, courses, currentQuarter, selectedCourseIndex)
        context.startActivity(intent)
    }
}