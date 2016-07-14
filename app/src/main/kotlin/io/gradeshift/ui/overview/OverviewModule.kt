package io.gradeshift.ui.overview

import dagger.Module
import dagger.Provides
import io.gradeshift.ui.ActivityScope

@Module
class OverviewModule {

    @Provides @ActivityScope
    fun provideOverviewUI(): OverviewUI = OverviewUI()

}