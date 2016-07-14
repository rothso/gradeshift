package io.gradeshift

import android.app.Application
import timber.log.Timber

class GradesApplication : Application() {

    companion object {
        @JvmStatic lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        graph = DaggerApplicationComponent.builder().build()
        Timber.d("Dagger graph initialized", graph)
    }
}