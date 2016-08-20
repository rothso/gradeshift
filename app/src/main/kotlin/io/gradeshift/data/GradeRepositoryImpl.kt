package io.gradeshift.data

import io.gradeshift.data.network.api.GradesApi
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Grade
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.model.Year
import io.gradeshift.domain.repository.GradeRepository
import rx.Observable

class GradeRepositoryImpl(val gradesApi: GradesApi) : GradeRepository {

    override fun getCoursesByQuarter(quarter: Quarter): Observable<List<Course>> {
        return gradesApi.getCourses(Year.DUMMY_YEAR, quarter)
    }

    override fun getGradesByCourse(course: Course, quarter: Quarter): Observable<List<Grade>> {
        return gradesApi.getGrades(Year.DUMMY_YEAR, quarter, course)
    }
}