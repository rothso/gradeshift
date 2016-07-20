package io.gradeshift.ui.overview

import dagger.Subcomponent
import io.gradeshift.ui.common.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(OverviewModule::class))
interface OverviewComponent {
    fun inject(overviewActivity: OverviewActivity)
}