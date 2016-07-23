package io.gradeshift.ui.quarter

import dagger.Module
import dagger.Provides
import io.gradeshift.domain.model.Quarter

@Module
class QuarterModule(private val quarter: Quarter) {

    @Provides @QuarterScope
    fun provideQuarter(): Quarter = quarter

    @Provides @QuarterScope
    fun provideUI(): QuarterUI = QuarterUI()
}