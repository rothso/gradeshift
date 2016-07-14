package io.gradeshift

import dagger.Component
import io.gradeshift.ui.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

}
