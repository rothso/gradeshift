package io.gradeshift.ui.course

import com.artemzin.rxui.kotlin.bind
import io.gradeshift.domain.GetCourseGradesInteractor
import io.gradeshift.domain.model.Grade
import io.gradeshift.ui.common.base.Presenter
import rx.Observable
import rx.Subscription
import rx.functions.Func1
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription

class CoursePresenter(val interactor: GetCourseGradesInteractor) : Presenter<CoursePresenter.View>() {

    override fun bind(view: View): Subscription {
        val subscription = CompositeSubscription()

        subscription += interactor.getCourseGrades()
            .bind(view.showGrades)

        return subscription
    }

    interface View {
        val showGrades: Func1<Observable<List<Grade>>, Subscription>
    }
}
