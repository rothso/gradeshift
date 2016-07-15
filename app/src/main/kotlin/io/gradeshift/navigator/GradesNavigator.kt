package io.gradeshift.navigator

import io.gradeshift.model.Class

interface GradesNavigator {
    fun showClasses(classes: List<Class>)
}