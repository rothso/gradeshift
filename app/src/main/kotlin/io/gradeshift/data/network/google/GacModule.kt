package io.gradeshift.data.network.google

import android.content.Context
import com.google.android.gms.common.api.GoogleApiClient
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class GacModule {

    @Provides @Singleton
    fun provideGoogleApiClient(context: Context): GoogleApiClient {
        return GoogleClient(context).gac
    }

    @Provides @Singleton
    fun provideSmartLock(googleApiClient: GoogleApiClient, @Named("Identity") identity: String): SmartLock {
        return SmartLock(googleApiClient, identity)
    }
}