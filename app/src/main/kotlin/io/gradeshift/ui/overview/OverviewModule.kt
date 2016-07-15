package io.gradeshift.ui.overview

import dagger.Module
import dagger.Provides
import io.gradeshift.model.Class
import io.gradeshift.ui.ActivityScope

@Module
class OverviewModule {

    @Provides @ActivityScope
    fun provideOverviewUI(): OverviewUI {
        // TODO: Presenter provides data during runtime
        val grades = listOf(
                Class("Chemistry", "Dr. HCL", 100),
                Class("History", "Henry VIII", 80),
                Class("Calculus", "Ms. Lady", 86)
        )
        val adapter = OverviewAdapter(grades)
        return OverviewUI(adapter)
    }

}