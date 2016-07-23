package io.gradeshift.data

import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Grade
import io.gradeshift.domain.model.Quarter
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GradeRepositoryImpl : GradeRepository {

    override fun getCoursesByQuarter(quarter: Quarter): Observable<List<Course>> {
        return Observable.defer { Observable.just(Course.DUMMY_COURSES) }
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
    }

    override fun getGradesByCourse(course: Course, inQuarter: Quarter): Observable<List<Grade>> {
        return Observable.defer { Observable.just(Grade.DUMMY_GRADES) }
                .map { it.map { it.copy(name = "[Course ${course.name}] ${it.name}") } }
                .subscribeOn(Schedulers.io())
    }

}