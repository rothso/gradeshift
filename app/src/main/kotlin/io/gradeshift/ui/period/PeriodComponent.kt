package io.gradeshift.ui.period

import dagger.Subcomponent
import io.gradeshift.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(PeriodModule::class))
interface PeriodComponent {
    fun inject(periodActivity: PeriodActivity)
}

