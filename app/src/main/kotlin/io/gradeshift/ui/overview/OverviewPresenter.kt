package io.gradeshift.ui.overview

//import io.gradeshift.ui.ext.plusAssign
import com.artemzin.rxui.kotlin.bind
import io.gradeshift.domain.OverviewInteractor
import io.gradeshift.model.Class
import io.gradeshift.ui.base.Presenter
import rx.Observable
import rx.Subscription
import rx.functions.Func1
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription

class OverviewPresenter(val interactor: OverviewInteractor) : Presenter<OverviewPresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()
        val classes = interactor.getClasses().share()

        // TODO handle network, etc. errors
        subscription += classes.bind(view.showClasses)
        subscription += Observable
                .combineLatest(classes, view.itemClicks, { classes, pos -> classes[pos] })
                .onBackpressureLatest()
                .bind(view.showClassDetail)

        // TODO onRefresh -> upstream update

        return subscription
    }

    interface View {
        val itemClicks: Observable<Int>
        val refreshes: Observable<Void>

        // Replace Func1<..> with a type alias when those puppies come out
        val showClasses: Func1<Observable<List<Class>>, Subscription>
        val showClassDetail: Func1<Observable<Class>, Subscription>
    }
}
