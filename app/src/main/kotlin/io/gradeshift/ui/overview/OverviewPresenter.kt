package io.gradeshift.ui.overview

import io.gradeshift.domain.OverviewInteractor
import io.gradeshift.model.Class
import io.gradeshift.ui.common.base.Presenter
import io.gradeshift.ui.common.ext.bind
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription

class OverviewPresenter(val interactor: OverviewInteractor) : Presenter<OverviewPresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        val refreshes: Observable<Unit> = view.refreshes.share()
                .startWith(Unit) // Trigger initial load

        val classes: Observable<List<Class>> = refreshes
                .switchMap { interactor.getClasses() }
                .share()

        // Loading and done loading
        subscription += refreshes
                .map { true }
                .bind(view.loading)
        subscription += classes
                .map { false }
                .bind(view.loading)

        // Content updates
        subscription += classes
                .distinctUntilChanged()
                .bind(view.showClasses)
        subscription += view.itemClicks
                .onBackpressureLatest()
                .bind(view.showClassDetail)

        // TODO handle network, etc. errors with onErrorReturn
        return subscription
    }

    interface View {
        val itemClicks: Observable<Class>
        val refreshes: Observable<Unit>

        // Replace with a type alias when those puppies come out
        val showClasses: (Observable<List<Class>>) -> Subscription
        val showClassDetail: (Observable<Class>) -> Subscription
        val loading: (Observable<Boolean>) -> Subscription
    }
}

