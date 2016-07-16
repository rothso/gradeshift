package io.gradeshift.ui.quarter

import dagger.Subcomponent
import io.gradeshift.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(QuarterModule::class))
interface QuarterComponent {
    fun inject(quarterActivity: QuarterActivity)
}

