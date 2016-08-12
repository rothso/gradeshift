package io.gradeshift.data

import io.gradeshift.data.network.api.GradesApi
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Grade
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.model.Year
import io.gradeshift.domain.repository.GradeRepository
import rx.Observable
import rx.schedulers.Schedulers

class GradeRepositoryImpl(val gradesApi: GradesApi) : GradeRepository {

    override fun getCoursesByQuarter(quarter: Quarter): Observable<List<Course>> {
        return gradesApi.getCourses(Year.DUMMY_YEAR, quarter)
    }

    override fun getGradesByCourse(course: Course, inQuarter: Quarter): Observable<List<Grade>> {
        return Observable.defer { Observable.just(Grade.DUMMY_GRADES) }
                .map { it.map { it.copy(name = "[Course ${course.name}] ${it.name}") } }
                .subscribeOn(Schedulers.io())
    }
}