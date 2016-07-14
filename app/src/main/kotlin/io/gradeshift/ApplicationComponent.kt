package io.gradeshift

import dagger.Component
import io.gradeshift.ui.main.MainComponent
import io.gradeshift.ui.main.MainModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun plus(mainModule: MainModule): MainComponent

}
