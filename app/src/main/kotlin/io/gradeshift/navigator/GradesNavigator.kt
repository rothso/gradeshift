package io.gradeshift.navigator

import io.gradeshift.model.Grade

interface GradesNavigator {
    fun showGrades(grades: List<Grade>)
}