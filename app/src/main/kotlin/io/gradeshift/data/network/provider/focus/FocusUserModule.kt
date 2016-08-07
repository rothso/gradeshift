package io.gradeshift.data.network.provider.focus

import com.ekchang.jsouper.Jsouper
import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.GradesApi
import io.gradeshift.data.network.auth.Authenticated
import io.gradeshift.data.network.auth.UserScope
import io.gradeshift.data.network.provider.focus.converter.CourseConverter
import io.gradeshift.domain.model.Course
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jsoup.JsoupConverterFactory
import javax.inject.Named

@Module
class FocusUserModule {

    @Provides @UserScope @Authenticated
    fun provideRetrofit(@Named("Endpoint") baseUrl: HttpUrl, @Authenticated client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JsoupConverterFactory.create(
                        Jsouper.Builder()
                                .add(Course::class.java, CourseConverter())
                                .build()
                ))
                .build()
    }

    @Provides @UserScope
    fun provideGradeApi(@Authenticated retrofit: Retrofit): GradesApi {
        return FocusGradesApi(retrofit.create(FocusGradesApi.HelperApi::class.java))
    }
}