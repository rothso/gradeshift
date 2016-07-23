package io.gradeshift.ui.course

import dagger.Module
import dagger.Provides
import io.gradeshift.data.GradeRepository
import io.gradeshift.domain.GetCourseGradesInteractor
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Quarter
import javax.inject.Provider

@Module
class CourseModule(private val course: Course) {

    @Provides @CourseScope
    fun providerAdapter(listener: CourseUI): CourseAdapter {
        return CourseAdapter(listener)
    }

    @Provides @CourseScope
    fun provideUI(adapterProvider: Provider<CourseAdapter>): CourseUI {
        return CourseUI(adapterProvider)
    }

    @Provides @CourseScope
    fun provideInteractor(repository: GradeRepository, quarter: Quarter): GetCourseGradesInteractor {
        return GetCourseGradesInteractor(repository, course, quarter)
    }

    @Provides @CourseScope
    fun providePresenter(interactor: GetCourseGradesInteractor): CoursePresenter {
        return CoursePresenter(interactor)
    }
}

