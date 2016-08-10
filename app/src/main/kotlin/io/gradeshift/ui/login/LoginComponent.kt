package io.gradeshift.ui.login

import dagger.Subcomponent
import io.gradeshift.ui.common.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(LoginModule::class))
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}