package io.gradeshift.ui.overview

import com.github.ajalt.timberkt.d
import com.google.android.gms.common.api.Status
import io.gradeshift.data.network.auth.exception.LoginRequiredException
import io.gradeshift.data.network.auth.exception.ResolutionRequiredException
import io.gradeshift.domain.GetQuarterCoursesInteractor
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.Quarter
import io.gradeshift.ui.common.base.Presenter
import io.gradeshift.ui.common.ext.bind
import rx.Observable
import rx.Subscription
import rx.exceptions.Exceptions
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription
import timber.log.Timber

class OverviewPresenter(private val interactor: GetQuarterCoursesInteractor) : Presenter<OverviewPresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        val refreshes: Observable<Unit> = view.refreshes
                .doOnNext { Timber.d("Refreshing!") }
                .share()
                .startWith(Unit) // Trigger initial load

        val courses: Observable<List<Course>> = let {
            val source = refreshes.switchMap { interactor.getCourses(Quarter.DUMMY_QUARTER) }
            source.onErrorResumeNext { error ->
                tailrec fun resolve(error: Throwable): Observable<out List<Course>> = when (error) {
                    is LoginRequiredException -> {
                        d { "Login required, showing login" }
                        view.showLogin()
                        Observable.empty()
                    }
                    is ResolutionRequiredException -> {
                        view.resolve(error.status)
                        source
                    }
                    else -> {
                        val cause = error.cause
                        if (error is RuntimeException && cause != null) {
                            resolve(cause)
                        } else {
                            d { "Passing error through" }
                            throw Exceptions.propagate(error)
                        }
                    }
                }
                resolve(error)
            }.share()
        }

        // Loading and done loading
        subscription += refreshes
                .map { true }
                .doOnNext { d { "Loading start" } }
                .bind(view.loading)

        subscription += courses
                .map { false }
                .defaultIfEmpty(false)
                .doOnNext { d { "Loading end" } }
                .bind(view.loading)

        // Content updates
        subscription += courses
                .distinctUntilChanged()
                .bind(view.showCourses)

        subscription += view.itemClicks
                .onBackpressureLatest()
                .withLatestFrom(courses, { i, xs -> Pair(i, xs) })
                .bind(view.showCourseDetail)

        // TODO handle network, etc. errors with onErrorReturn
        return subscription
    }

    interface AuthResolvableView {
        fun resolve(status: Status)
        fun showLogin()
    }

    interface View : AuthResolvableView {
        val itemClicks: Observable<Int>
        val refreshes: Observable<Unit>

        // Replace with a type alias when those puppies come out
        val showCourses: (Observable<List<Course>>) -> Subscription
        val showCourseDetail: (Observable<Pair<Int, List<Course>>>) -> Subscription
        val loading: (Observable<Boolean>) -> Subscription
    }
}
