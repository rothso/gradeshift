package io.gradeshift.data.network.auth

import dagger.Subcomponent
import io.gradeshift.data.DataModule
import io.gradeshift.data.network.provider.focus.FocusUserModule
import io.gradeshift.ui.overview.OverviewComponent
import io.gradeshift.ui.overview.OverviewModule
import io.gradeshift.ui.quarter.QuarterComponent
import io.gradeshift.ui.quarter.QuarterModule

@UserScope
@Subcomponent(modules = arrayOf(
        UserModule::class,
        DataModule::class,
        FocusUserModule::class
))
interface UserComponent {
    fun plus(overviewModule: OverviewModule): OverviewComponent
    fun plus(quarterModule: QuarterModule): QuarterComponent

    fun user(): User
}

