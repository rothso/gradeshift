package io.gradeshift

import dagger.Component
import io.gradeshift.ui.main.MainComponent
import io.gradeshift.ui.main.MainModule
import io.gradeshift.ui.overview.OverviewComponent
import io.gradeshift.ui.overview.OverviewModule
import io.gradeshift.ui.quarter.QuarterComponent
import io.gradeshift.ui.quarter.QuarterModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun plus(mainModule: MainModule): MainComponent
    fun plus(overviewModule: OverviewModule): OverviewComponent
    fun plus(quarterModule: QuarterModule): QuarterComponent
}

