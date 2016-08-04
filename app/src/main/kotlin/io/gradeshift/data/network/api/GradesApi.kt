package io.gradeshift.data.network.api

import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.model.Year
import rx.Observable

interface GradesApi {
    fun getCourses(year: Year, quarter: Quarter): Observable<List<Course>>
}