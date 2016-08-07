package io.gradeshift.data

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.gradeshift.data.network.api.GradesApi
import io.gradeshift.data.network.auth.Authenticated
import io.gradeshift.data.network.auth.User
import io.gradeshift.data.network.auth.UserScope
import javax.inject.Named

@Module
class DataModule {
    // TODO evaluate the "implicit user" approach

    @Provides @UserScope @Authenticated
    fun providePrefs(user: User, @Named("Namespace") provider: String, context: Context): SharedPreferences {
        return context.getSharedPreferences("$provider.${user.id}", Context.MODE_PRIVATE)
    }

    @Provides @UserScope
    fun provideGradesRepository(gradesApi: GradesApi): GradeRepository {
        return GradeRepositoryImpl(gradesApi)
    }
}