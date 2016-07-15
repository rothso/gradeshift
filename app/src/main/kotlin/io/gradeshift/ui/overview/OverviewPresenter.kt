package io.gradeshift.ui.overview

import com.artemzin.rxui.kotlin.bind
import io.gradeshift.model.Class
import io.gradeshift.ui.ext.plusAssign
import rx.Observable
import rx.Subscription
import rx.functions.Func1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import java.util.concurrent.TimeUnit

class OverviewPresenter {

    fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        subscription += fetchClasses().bind(view.showClasses)
        subscription += view.itemClicks.subscribe { position -> Timber.d(position.toString()) }

        return subscription
    }

    private fun fetchClasses() : Observable<List<Class>> =
        Observable.defer { Observable.just(Class.DUMMY_CLASSES) }
                .delay(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())

    interface View {
        val itemClicks: Observable<Int>
        // Replace Func1<..> with a type alias when those puppies come out
        val showClasses: Func1<Observable<List<Class>>, Subscription>
    }
}