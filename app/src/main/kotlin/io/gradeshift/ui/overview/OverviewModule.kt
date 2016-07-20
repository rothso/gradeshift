package io.gradeshift.ui.overview

import dagger.Module
import dagger.Provides
import io.gradeshift.domain.OverviewInteractor
import io.gradeshift.ui.common.ActivityScope
import javax.inject.Provider

@Module
class OverviewModule(val context: OverviewActivity) {

    @Provides @ActivityScope
    fun provideNavigator(): Navigator = Navigator(context)

    @Provides @ActivityScope
    fun provideAdapter(listener: OverviewUI): OverviewAdapter = OverviewAdapter(listener)

    @Provides @ActivityScope
    fun provideUI(a: Provider<OverviewAdapter>, n: Navigator): OverviewUI = OverviewUI(a, n)

    @Provides @ActivityScope
    fun provideInteractor(): OverviewInteractor = OverviewInteractor()

    @Provides @ActivityScope
    fun providePresenter(i: OverviewInteractor): OverviewPresenter = OverviewPresenter(i)

}