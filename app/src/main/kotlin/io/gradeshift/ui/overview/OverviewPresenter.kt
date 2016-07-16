package io.gradeshift.ui.overview

//import io.gradeshift.ui.ext.plusAssign
import com.artemzin.rxui.kotlin.bind
import io.gradeshift.model.Class
import rx.Observable
import rx.Subscription
import rx.functions.Func1
import rx.lang.kotlin.plusAssign
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

class OverviewPresenter {

    fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        val classes = fetchClasses().share()

        subscription += classes.bind(view.showClasses)
        subscription += Observable
                .combineLatest(classes, view.itemClicks, { classes, pos -> classes[pos] })
                .onBackpressureLatest()
                .bind(view.showClassDetail)

        return subscription
    }

    private fun fetchClasses(): Observable<List<Class>> =
            Observable.defer { Observable.just(Class.DUMMY_CLASSES) }
                    .delay(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())

    interface View {
        val itemClicks: Observable<Int>
        // Replace Func1<..> with a type alias when those puppies come out
        val showClasses: Func1<Observable<List<Class>>, Subscription>
        val showClassDetail: Func1<Observable<Class>, Subscription>
    }
}