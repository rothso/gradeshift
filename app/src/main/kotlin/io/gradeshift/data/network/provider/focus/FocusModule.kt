package io.gradeshift.data.network.provider.focus

import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.LoginApi
import io.gradeshift.data.network.auth.AuthInterceptor
import io.gradeshift.data.network.auth.Token
import okhttp3.HttpUrl
import okhttp3.Request
import javax.inject.Named
import javax.inject.Singleton

@Module
class FocusModule {

    companion object {
        private const val NAMESPACE = "com.focusschoolsoftware.dcps" // SharedPreferences ns
        private const val IDENTITY_DCPS_FOCUS = "https://dcps.focusschoolsoftware.com" // SmartLock
        private const val ENDPOINT = "http://127.0.0.1:4000/api/" // REST
    }

    @Provides @Singleton @Named("Namespace")
    fun provideNamespace(): String = NAMESPACE

    @Provides @Singleton @Named("Identity")
    fun provideIdentity(): String = IDENTITY_DCPS_FOCUS

    @Provides @Singleton @Named("Endpoint")
    fun provideEndpoint(): HttpUrl = HttpUrl.parse(ENDPOINT)

    @Provides @Singleton
    fun provideAuthenticator(): AuthInterceptor.Decorator {
        return object : AuthInterceptor.Decorator {
            override fun decorate(request: Request, token: Token) = throw UnsupportedOperationException()
        }
    }

    @Provides @Singleton
    fun provideLoginApi(): LoginApi {
        return object : LoginApi {
            override fun login(username: String, password: String) = throw UnsupportedOperationException()
        }
    }

}