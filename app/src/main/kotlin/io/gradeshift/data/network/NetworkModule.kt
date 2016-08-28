package io.gradeshift.data.network

import com.franmontiel.persistentcookiejar.cache.CookieCache
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides @Singleton
    fun provideCookieCache(): CookieCache {
        return SetCookieCache()
    }

    @Provides @Singleton
    fun provideClient(cookieCache: CookieCache): OkHttpClient {
        return OkHttpClient.Builder()
                .cookieJar(CacheCookieJar(cookieCache))
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
    }

    @Provides @Singleton
    fun provideRetrofit(@Named("Endpoint") baseUrl: HttpUrl, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }

}