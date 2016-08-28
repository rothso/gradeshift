package io.gradeshift.data.network.provider.focus

import com.franmontiel.persistentcookiejar.cache.CookieCache
import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.LoginApi
import io.gradeshift.data.network.auth.AuthInterceptor
import io.gradeshift.data.network.provider.focus.converter.FocusLoginApi
import okhttp3.HttpUrl
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class FocusModule {

    companion object {
        private const val NAMESPACE = "com.focusschoolsoftware.dcps" // SharedPreferences ns
        private const val IDENTITY_DCPS_FOCUS = "https://dcps.focusschoolsoftware.com" // SmartLock
        private const val ENDPOINT = "http://127.0.0.1:4000/focus/" // REST
    }

    @Provides @Singleton @Named("Namespace")
    fun provideNamespace(): String = NAMESPACE

    @Provides @Singleton @Named("Identity")
    fun provideIdentity(): String = IDENTITY_DCPS_FOCUS

    @Provides @Singleton @Named("Endpoint")
    fun provideEndpoint(): HttpUrl = HttpUrl.parse(ENDPOINT)

    @Provides @Singleton
    fun provideAuthDecorator(): AuthInterceptor.Decorator {
        return FocusAuthDecorator()
    }

    @Provides @Singleton
    fun provideLoginApi(retrofit: Retrofit, cookieCache: CookieCache): LoginApi {
        return FocusLoginApi(retrofit, cookieCache)
    }
}