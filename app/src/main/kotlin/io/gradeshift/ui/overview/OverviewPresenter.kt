package io.gradeshift.ui.overview

import io.gradeshift.domain.GetQuarterCoursesInteractor
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Quarter
import io.gradeshift.ui.common.base.Presenter
import io.gradeshift.ui.common.ext.bind
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

class OverviewPresenter(val interactor: GetQuarterCoursesInteractor) : Presenter<OverviewPresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        val refreshes: Observable<Unit> = view.refreshes
                .doOnNext { Timber.d("Refreshing!") }
                .share()
                .startWith(Unit) // Trigger initial load

        val courses: Observable<List<Course>> = refreshes
                .switchMap { interactor.getCourses(Quarter.DUMMY_QUARTER) }
                .share()

        // Loading and done loading
        subscription += refreshes.map { true }.doOnNext { Timber.d("Loading") }.bind(view.loading)
        subscription += courses.map { false }.doOnNext { Timber.d("Done loading") }.bind(view.loading)

        // Content updates
        subscription += courses
                .distinctUntilChanged()
                .bind(view.showCourses)
        subscription += view.itemClicks
                .onBackpressureLatest()
                .withLatestFrom(courses, {i, xs -> Pair(i, xs)})
                .bind(view.showCourseDetail)

        // TODO handle network, etc. errors with onErrorReturn
        return subscription
    }

    interface View {
        val itemClicks: Observable<Int>
        val refreshes: Observable<Unit>

        // Replace with a type alias when those puppies come out
        val showCourses: (Observable<List<Course>>) -> Subscription
        val showCourseDetail: (Observable<Pair<Int, List<Course>>>) -> Subscription
        val loading: (Observable<Boolean>) -> Subscription
    }
}

