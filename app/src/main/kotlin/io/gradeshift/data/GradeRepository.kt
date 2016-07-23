package io.gradeshift.data
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Grade
import io.gradeshift.domain.model.Quarter
import rx.Observable

interface GradeRepository {

    fun getCoursesByQuarter(quarter: Quarter): Observable<List<Course>>

    fun getGradesByCourse(course: Course, inQuarter: Quarter): Observable<List<Grade>>

}