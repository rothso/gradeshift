package io.gradeshift.ui.quarter

import dagger.Module
import dagger.Provides
import io.gradeshift.domain.QuarterGradesInteractor
import io.gradeshift.ui.ActivityScope
import javax.inject.Provider

@Module
class QuarterModule(val classId: Int) {

    // TODO uniform order

    @Provides @ActivityScope
    fun providerAdapter(listener: QuarterUI): QuarterAdapter = QuarterAdapter(listener)

    @Provides @ActivityScope
    fun provideInteractor(): QuarterGradesInteractor = QuarterGradesInteractor(classId)

    @Provides @ActivityScope
    fun providePresenter(interactor: QuarterGradesInteractor): QuarterPresenter {
        return QuarterPresenter(interactor)
    }

    @Provides @ActivityScope
    fun provideUI(adapterProvider: Provider<QuarterAdapter>): QuarterUI = QuarterUI(adapterProvider)
}
