package io.gradeshift.ui.main

import dagger.Subcomponent
import io.gradeshift.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}