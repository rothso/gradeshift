package io.gradeshift

import dagger.Component
import io.gradeshift.data.network.auth.UserComponent
import io.gradeshift.data.network.auth.UserModule
import io.gradeshift.data.network.provider.focus.FocusModule
import io.gradeshift.ui.login.LoginComponent
import io.gradeshift.ui.login.LoginModule
import io.gradeshift.ui.main.MainComponent
import io.gradeshift.ui.main.MainModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        FocusModule::class))
interface ApplicationComponent {
    fun inject(application: GradesApplication)

    fun plus(userModule: UserModule): UserComponent
    fun plus(mainModule: MainModule): MainComponent
    fun plus(loginModule: LoginModule): LoginComponent
}