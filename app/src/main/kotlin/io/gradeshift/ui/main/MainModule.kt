package io.gradeshift.ui.main

import dagger.Module
import dagger.Provides
import io.gradeshift.ui.ActivityScope

@Module
class MainModule {

    @Provides @ActivityScope
    fun provideMainUI(): MainUI = MainUI()

}