package io.gradeshift

import android.app.Application
import com.facebook.stetho.Stetho
import com.google.android.gms.common.api.GoogleApiClient
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import io.gradeshift.data.network.auth.User
import io.gradeshift.data.network.auth.UserComponent
import io.gradeshift.data.network.auth.UserModule
import timber.log.Timber
import javax.inject.Inject

class GradesApplication : Application() {
    @Inject lateinit var gac: GoogleApiClient

    companion object {
        lateinit var graph: ApplicationComponent
        lateinit var userGraph: UserComponent
    }

    override fun onCreate() {
        super.onCreate()

        // Java 8 Time
        AndroidThreeTen.init(this)

        // Debugging tools
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
            LeakCanary.install(this)
        }

        graph = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        graph.inject(this)

        userGraph = graph.plus(UserModule(User.DUMMY_USER))

        Timber.d("Dagger graph initialized", graph)
    }
}