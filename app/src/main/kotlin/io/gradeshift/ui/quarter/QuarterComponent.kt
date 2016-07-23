package io.gradeshift.ui.quarter

import dagger.Subcomponent
import io.gradeshift.data.GradeRepository
import io.gradeshift.domain.model.Quarter

@QuarterScope
@Subcomponent(modules = arrayOf(QuarterModule::class))
interface QuarterComponent {
    fun inject(quarterActivity: QuarterActivity)
    fun gradeRepository(): GradeRepository
    fun quarter(): Quarter
}
