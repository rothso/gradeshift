package io.gradeshift.data.network

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient()
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