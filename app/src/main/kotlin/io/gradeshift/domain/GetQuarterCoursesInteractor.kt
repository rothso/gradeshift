package io.gradeshift.domain

import io.gradeshift.data.GradeRepository
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Quarter
import rx.Observable

class GetQuarterCoursesInteractor(
        private val repository: GradeRepository
) {

    fun getCourses(quarter: Quarter): Observable<List<Course>> {
        return repository.getCoursesByQuarter(quarter)
    }
}
