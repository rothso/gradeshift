package io.gradeshift.ui.common.ext

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.subscribeWith

/**
 * Translated from RxUI (Artem Zinnatullin)
 */

fun <T> Observable<T>.bind(uiFunc: (Observable<T>) -> Subscription): Subscription {
    return uiFunc.invoke(this)
}

fun <T> ui(onNext: (T) -> Unit): (Observable<T>) -> Subscription = {
    it.observeOn(AndroidSchedulers.mainThread()).subscribeWith {
        onNext(onNext)
    }
}