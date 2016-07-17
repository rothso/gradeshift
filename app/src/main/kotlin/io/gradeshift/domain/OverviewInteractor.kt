package io.gradeshift.domain

import io.gradeshift.model.Class
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class OverviewInteractor {

    fun getClasses(): Observable<List<Class>> =
            Observable.defer { Observable.just(Class.DUMMY_CLASSES) }
                    .delay(2, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
}