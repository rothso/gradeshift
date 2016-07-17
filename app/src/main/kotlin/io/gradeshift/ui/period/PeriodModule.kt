package io.gradeshift.ui.period

import dagger.Module
import dagger.Provides
import io.gradeshift.domain.QuarterGradesInteractor
import io.gradeshift.ui.ActivityScope
import javax.inject.Provider

@Module
class PeriodModule(val classId: Int) {

    @Provides @ActivityScope
    fun providerAdapter(listener: PeriodUI): PeriodAdapter = PeriodAdapter(listener)

    @Provides @ActivityScope
    fun provideUI(adapterProvider: Provider<PeriodAdapter>): PeriodUI = PeriodUI(adapterProvider)

    @Provides @ActivityScope
    fun provideInteractor(): QuarterGradesInteractor = QuarterGradesInteractor(classId)

    @Provides @ActivityScope
    fun providePresenter(interactor: QuarterGradesInteractor): PeriodPresenter {
        return PeriodPresenter(interactor)
    }
}
