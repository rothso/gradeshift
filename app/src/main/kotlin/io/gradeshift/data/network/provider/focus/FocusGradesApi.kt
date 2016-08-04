package io.gradeshift.data.network.provider.focus

import io.gradeshift.data.network.api.GradesApi
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.model.Year
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable
import rx.schedulers.Schedulers

class FocusGradesApi(val api: HelperApi) : GradesApi {

    interface HelperApi {
        @POST("Modules.php?modname=misc/Portal.php") @FormUrlEncoded
        fun getCourses(@Field("side_syear") year: Int, @Field("side_mp") quarter: Int): Observable<List<Course>>
    }

    override fun getCourses(year: Year, quarter: Quarter): Observable<List<Course>> {
        return api.getCourses(year.id, quarter.id).subscribeOn(Schedulers.io())
    }
}