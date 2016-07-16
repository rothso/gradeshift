package io.gradeshift.ui.quarter

import com.artemzin.rxui.kotlin.bind
import io.gradeshift.domain.QuarterGradesInteractor
import io.gradeshift.model.Grade
import io.gradeshift.ui.base.Presenter
import rx.Observable
import rx.Subscription
import rx.functions.Func1
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription

class QuarterPresenter(val interactor: QuarterGradesInteractor) : Presenter<QuarterPresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        subscription += interactor.getCurrentQuarterGrades()
            .bind(view.showGrades)

        return subscription
    }

    interface View {
        val showGrades: Func1<Observable<List<Grade>>, Subscription>
    }
}
