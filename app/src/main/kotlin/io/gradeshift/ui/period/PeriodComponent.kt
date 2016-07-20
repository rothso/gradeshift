package io.gradeshift.ui.period

import dagger.Subcomponent
import io.gradeshift.ui.common.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(PeriodModule::class))
interface PeriodComponent {
    fun inject(periodFragment: PeriodFragment)
}

