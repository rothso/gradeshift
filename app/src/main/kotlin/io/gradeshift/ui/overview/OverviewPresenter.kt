package io.gradeshift.ui.overview

import io.gradeshift.model.Class
import io.gradeshift.ui.ext.plusAssign
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import java.util.concurrent.TimeUnit

class OverviewPresenter {

    fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        subscription += fetchClasses().subscribe { view.showClasses(it) }
        subscription += view.itemClicks.subscribe { position -> Timber.d(position.toString()) }

        return subscription
    }

    private fun fetchClasses() : Observable<List<Class>> =
        Observable.defer { Observable.just(Class.DUMMY_CLASSES) }
                .delay(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    interface View {
        val itemClicks: Observable<Int>
        fun showClasses(classes: List<Class>)
    }
}