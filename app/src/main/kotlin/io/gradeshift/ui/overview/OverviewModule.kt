package io.gradeshift.ui.overview

import dagger.Module
import dagger.Provides
import io.gradeshift.ui.ActivityScope
import javax.inject.Provider

@Module
class OverviewModule {

    @Provides @ActivityScope
    fun providePresenter(): OverviewPresenter = OverviewPresenter()

    @Provides @ActivityScope
    fun provideAdapter(listener: OverviewUI): OverviewAdapter = OverviewAdapter(listener)

    @Provides @ActivityScope
    fun provideUI(adapter: Provider<OverviewAdapter>): OverviewUI = OverviewUI(adapter)

}