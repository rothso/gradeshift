package io.gradeshift

import android.app.Application
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.gradeshift.data.GradeRepository
import io.gradeshift.data.GradeRepositoryImpl
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides @Singleton
    fun provideGradeRepository(): GradeRepository {
        return GradeRepositoryImpl()
    }

    @Provides @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        // TODO [1.x] user-specific shared prefs
        return application.defaultSharedPreferences
    }

}