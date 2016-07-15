package io.gradeshift.ui.overview

import dagger.Module
import dagger.Provides
import io.gradeshift.ui.ActivityScope
import io.gradeshift.model.Grade

@Module
class OverviewModule {

    @Provides @ActivityScope
    fun provideOverviewUI(): OverviewUI {
        // TODO: Presenter provides data during runtime
        val grades = listOf(
                Grade("Homework 1", 100),
                Grade("Homework 2", 80),
                Grade("Test", 86)
        )
        val adapter = OverviewAdapter(grades)
        return OverviewUI(adapter)
    }

}