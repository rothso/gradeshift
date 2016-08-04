package io.gradeshift.data.util

import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Result
import rx.Observable
import rx.Observer
import rx.observables.SyncOnSubscribe

class PendingResultObservable private constructor() {

    companion object {
        fun <R : Result> from(pendingResult: PendingResult<R>): Observable<R> {
            val generator = { pendingResult }
            val next = { result: PendingResult<R>, subscriber: Observer<in R> ->
                result.setResultCallback {
                    subscriber.onNext(it)
                    subscriber.onCompleted()
                }
            }
            val unsubscribe = { result: PendingResult<R> -> result.cancel() }
            return Observable.create(SyncOnSubscribe.createSingleState(generator, next, unsubscribe))
        }
    }
}