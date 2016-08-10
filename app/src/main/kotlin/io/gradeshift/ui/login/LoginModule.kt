package io.gradeshift.ui.login

import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.google.SmartLock
import io.gradeshift.ui.common.ActivityScope

@Module
class LoginModule {

    @Provides @ActivityScope
    fun provideUI(): LoginUI = LoginUI()

    @Provides @ActivityScope
    fun providePresenter(smartLock: SmartLock): LoginPresenter = LoginPresenter(smartLock)
}