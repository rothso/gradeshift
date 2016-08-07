package io.gradeshift

import dagger.Component
import io.gradeshift.data.network.auth.UserComponent
import io.gradeshift.data.network.auth.UserModule
import io.gradeshift.data.network.provider.focus.FocusModule
import io.gradeshift.ui.main.MainComponent
import io.gradeshift.ui.main.MainModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        FocusModule::class))
interface ApplicationComponent {
    fun plus(userModule: UserModule): UserComponent
    fun plus(mainModule: MainModule): MainComponent
}