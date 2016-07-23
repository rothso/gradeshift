package io.gradeshift.domain

import io.gradeshift.data.GradeRepository
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Grade
import io.gradeshift.domain.model.Quarter
import rx.Observable

class GetCourseGradesInteractor(
        private val repository: GradeRepository,
        private val course: Course,
        private val ofQuarter: Quarter
) {

    fun getCourseGrades(): Observable<List<Grade>> {
        return repository.getGradesByCourse(course, ofQuarter)
    }
}