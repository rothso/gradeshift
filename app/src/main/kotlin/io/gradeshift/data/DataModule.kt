package io.gradeshift.data

import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.GradesApi
import io.gradeshift.data.network.auth.UserScope

@Module
class DataModule {

    @Provides @UserScope
    fun provideGradesRepository(gradesApi: GradesApi): GradeRepository {
        return GradeRepositoryImpl(gradesApi)
    }
}