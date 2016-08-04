package io.gradeshift.data.network.provider.focus

import com.ekchang.jsouper.Jsouper
import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.GradesApi
import io.gradeshift.data.network.api.LoginApi
import io.gradeshift.data.network.auth.Authenticator
import io.gradeshift.data.network.auth.Token
import io.gradeshift.data.network.auth.UserModule
import io.gradeshift.data.network.auth.UserScope
import io.gradeshift.data.network.provider.focus.converter.CourseConverter
import io.gradeshift.domain.model.Course
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jsoup.JsoupConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class FocusModule {

    companion object {
        private const val IDENTITY_DCPS_FOCUS = "https://dcps.focusschoolsoftware.com" // SmartLock
        //        private const val ENDPOINT = "https://duval.focusschoolsoftware.com/focus/" // REST
        private const val ENDPOINT = "http://127.0.0.1:4000/api/" // REST
    }

    @Provides @Singleton @Named("Identity")
    fun provideAuthIdentity(): String = IDENTITY_DCPS_FOCUS

    @Provides @Singleton @Named("Endpoint")
    fun provideEndpoint(): HttpUrl = HttpUrl.parse(ENDPOINT)

    @Provides @Singleton
    fun provideAuthenticator(): Authenticator {
        return object : Authenticator {
            override fun authorize(request: Request, token: Token) = throw UnsupportedOperationException()
        }
    }

    @Provides @Singleton
    fun provideLoginApi(): LoginApi {
        return object : LoginApi {
            override fun login(username: String, password: String) = throw UnsupportedOperationException()
        }
    }

    @Module
    class Authenticated {

        @Provides @UserScope @Named(UserModule.AUTHENTICATED)
        fun provideRetrofit(@Named("Endpoint") baseUrl: HttpUrl, @Named(UserModule.AUTHENTICATED) client: OkHttpClient): Retrofit {
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
        fun provideGradeApi(@Named(UserModule.AUTHENTICATED) retrofit: Retrofit): GradesApi {
            return FocusGradesApi(retrofit.create(FocusGradesApi.HelperApi::class.java))
        }
    }
}