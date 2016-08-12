package io.gradeshift

import android.app.Application
import com.facebook.stetho.Stetho
import com.github.ajalt.timberkt.d
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import io.gradeshift.data.network.auth.User
import io.gradeshift.data.network.auth.UserComponent
import io.gradeshift.data.network.auth.UserModule
import io.gradeshift.domain.service.Authenticator
import io.gradeshift.ui.main.CheckLogin
import rx.Observable
import timber.log.Timber

class GradesApplication : Application(), CheckLogin, Authenticator.Callback {

    companion object {
        lateinit var graph: ApplicationComponent
        lateinit var userGraph: UserComponent // TODO should be nullable
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

        Timber.d("Dagger graph initialized", graph)
    }

    override fun isLoggedIn(): Observable<Boolean> {
        return Observable.fromCallable {
            try {
                userGraph.user()
                d { "User logged in" }
                true
            } catch (e: UninitializedPropertyAccessException) {
                d { "User not logged in" }
                false
            }
        }
    }

    override fun userLoggedIn(user: User) {
        userGraph = graph.plus(UserModule(user))
    }
}