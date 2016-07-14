package io.gradeshift

import dagger.Module
import dagger.Provides
import io.gradeshift.ui.main.MainPresenter
import io.gradeshift.ui.main.MainUI
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides @Singleton
    fun provideMainPresenter(): MainPresenter = MainPresenter()

    @Provides @Singleton
    fun provideMainUI(presenter: MainPresenter): MainUI = MainUI(presenter)

}