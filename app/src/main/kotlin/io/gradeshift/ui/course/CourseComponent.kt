package io.gradeshift.ui.course

import dagger.Component
import io.gradeshift.ui.quarter.QuarterComponent

@CourseScope
@Component(modules = arrayOf(CourseModule::class), dependencies = arrayOf(QuarterComponent::class))
interface CourseComponent {
    fun inject(courseFragment: CourseFragment)
}

