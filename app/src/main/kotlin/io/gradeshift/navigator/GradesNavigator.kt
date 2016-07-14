package io.gradeshift.navigator

import io.gradeshift.ui.main.Grade

interface GradesNavigator {
    fun showGrades(grades: List<Grade>)
}