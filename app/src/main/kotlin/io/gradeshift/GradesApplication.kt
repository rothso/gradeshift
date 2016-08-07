package io.gradeshift

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import io.gradeshift.data.network.auth.User
import io.gradeshift.data.network.auth.UserComponent
import io.gradeshift.data.network.auth.UserModule
import timber.log.Timber

class GradesApplication : Application() {

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

        userGraph = graph.plus(UserModule(User.DUMMY_USER))

        Timber.d("Dagger graph initialized", graph)
    }
}