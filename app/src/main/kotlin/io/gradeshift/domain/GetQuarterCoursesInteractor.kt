package io.gradeshift.domain

import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.repository.GradeRepository
import rx.Observable

class GetQuarterCoursesInteractor(
        private val repository: GradeRepository
) {

    fun getCourses(quarter: Quarter): Observable<List<Course>> {
        return repository.getCoursesByQuarter(quarter)
    }
}
