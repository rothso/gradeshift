package io.gradeshift.ui.overview

import io.gradeshift.model.Class
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class OverviewPresenter {

    private var view: View? = null

    fun attachView(view: View) {
        this.view = view
        fetchGrades()
    }

    fun fetchGrades() {
        // TODO show loading
        val grades = listOf(
                Class("Chemistry", "Dr. HCL", 100),
                Class("History", "Henry VIII", 80),
                Class("Calculus", "Ms. Lady", 86)
        )
        Observable.defer { Observable.just(grades) }
                .delay(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view?.showClasses(it) }
    }

    interface View {
        fun showClasses(classes: List<Class>)
    }
}