package io.gradeshift.ui.overview

import io.gradeshift.ui.main.Grade
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class OverviewPresenter {

    private var view: View? = null

    fun attachView(view: View) {
        this.view = view
    }

    fun fetchGrades() {
        Observable.defer {
            Observable.just(listOf(
                    Grade("Homework 1", 100),
                    Grade("Homework 2", 80),
                    Grade("Test", 86)
            ))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view?.showGrades(it) }
    }

    interface View {
        fun showGrades(grades: List<Grade>)
    }
}