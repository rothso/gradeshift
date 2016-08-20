package io.gradeshift.data.network.provider.focus

import io.gradeshift.data.network.api.GradesApi
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Grade
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.model.Year
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable
import rx.schedulers.Schedulers

class FocusGradesApi(val api: HelperApi) : GradesApi {

    interface HelperApi {
        companion object {
            private const val YEAR: String = "side_syear"
            private const val QUARTER: String = "side_mp"
        }

        @POST("Modules.php?modname=misc/Portal.php") @FormUrlEncoded
        fun getCourses(@Field(YEAR) year: Int, @Field(QUARTER) quarter: Int): Observable<List<Course>>

        @POST("Modules.php") @FormUrlEncoded
        fun getGrades(@Field(YEAR) year: Int, @Field(QUARTER) quarter: Int, @Query("modname") modName: String): Observable<List<Grade>>
    }

    override fun getCourses(year: Year, quarter: Quarter): Observable<List<Course>> {
        return api.getCourses(year.id, quarter.id)
                .subscribeOn(Schedulers.io())
    }

    override fun getGrades(year: Year, quarter: Quarter, course: Course): Observable<List<Grade>> {
        return api.getGrades(year.id, quarter.id, "Grades/StudentGBGrades.php?course_period_id=${course.id}")
                .subscribeOn(Schedulers.io())
    }
}
