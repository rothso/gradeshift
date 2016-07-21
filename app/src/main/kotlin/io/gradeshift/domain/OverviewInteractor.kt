package io.gradeshift.domain

import com.jakewharton.rxrelay.BehaviorRelay
import io.gradeshift.model.Class
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class OverviewInteractor {
    private val classesRelay = BehaviorRelay.create(Class.DUMMY_CLASSES)
    private val slowClasses = Observable.defer { Observable.just(Class.DUMMY_CLASSES) }
            .map { it.map { it.copy(name = "${it.name} v2") } }
            .delay(3, TimeUnit.SECONDS)

    fun getClasses(): Observable<List<Class>> = classesRelay
            .mergeWith(slowClasses)
            .subscribeOn(Schedulers.io())
}