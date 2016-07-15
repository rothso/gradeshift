package io.gradeshift.ui.overview

import dagger.Module
import dagger.Provides
import io.gradeshift.ui.ActivityScope

@Module
class OverviewModule {

    @Provides @ActivityScope
    fun providePresenter(): OverviewPresenter = OverviewPresenter()

    @Provides @ActivityScope
    fun provideUI(): OverviewUI = OverviewUI(OverviewAdapter())

}