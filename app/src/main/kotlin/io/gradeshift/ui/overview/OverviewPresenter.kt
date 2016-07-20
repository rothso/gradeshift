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
        val classes = interactor.getClasses().share()

        // TODO handle network, etc. errors with onErrorReturn
        subscription += classes.bind(view.showClasses)
        subscription += Observable
                .combineLatest(classes, view.itemClicks, { classes, pos -> classes[pos] })
                .onBackpressureLatest()
                .bind(view.showClassDetail)

        // TODO onRefresh -> upstream update
        // TODO handle showLoading in the Presenter only

        return subscription
    }

    interface View {
        val itemClicks: Observable<Int>
        val refreshes: Observable<Void>

        // Replace Func1<..> with a type alias when those puppies come out
        val showClasses: (Observable<List<Class>>) -> Subscription
        val showClassDetail: (Observable<Class>) -> Subscription
    }
}

