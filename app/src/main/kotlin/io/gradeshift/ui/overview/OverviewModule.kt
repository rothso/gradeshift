package io.gradeshift.ui.overview

import dagger.Module
import dagger.Provides
import io.gradeshift.domain.GetQuarterCoursesInteractor
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.repository.GradeRepository
import io.gradeshift.ui.common.ActivityScope

@Module
class OverviewModule(
        private val context: OverviewActivity,
        private val currentQuarter: Quarter // Hard-scope to a quarter until we add hotswap fragments
) {

    @Provides @ActivityScope
    fun provideNavigator(): Navigator {
        return Navigator(context, currentQuarter)
    }

    @Provides @ActivityScope
    fun provideAdapter(): OverviewAdapter {
        return OverviewAdapter(context)
    }

    @Provides @ActivityScope
    fun provideUI(): OverviewUI {
        return OverviewUI()
    }

    // TODO [0.x] pass a (maybe) quarter-scoped data sStore instead
    @Provides @ActivityScope
    fun provideInteractor(repository: GradeRepository): GetQuarterCoursesInteractor {
        return GetQuarterCoursesInteractor(repository)
    }

    @Provides @ActivityScope
    fun providePresenter(i: GetQuarterCoursesInteractor): OverviewPresenter = OverviewPresenter(i)

}