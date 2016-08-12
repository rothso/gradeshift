package io.gradeshift.ui.main

import android.content.Context
import dagger.Module
import dagger.Provides
import io.gradeshift.ui.common.ActivityScope

@Module
class MainModule {

    @Provides @ActivityScope
    fun provideMainUI(): MainUI = MainUI()

    @Provides @ActivityScope
    fun providePresenter(context: Context): MainPresenter {
        return MainPresenter(context as CheckLogin)
    }
}