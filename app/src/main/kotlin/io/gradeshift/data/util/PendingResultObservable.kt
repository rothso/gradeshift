package io.gradeshift.data.util

import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Result
import rx.Observable
import rx.Observer
import rx.observables.SyncOnSubscribe
import rx.schedulers.Schedulers

class PendingResultObservable private constructor() {

    companion object {
        fun <R : Result> from(pendingResult: PendingResult<R>): Observable<R> {
            val next = { subscriber: Observer<in R> ->
                subscriber.onNext(pendingResult.await())
                subscriber.onCompleted()
            }
            val unsubscribe = { pendingResult.cancel() }
            return Observable.create(SyncOnSubscribe.createStateless(next, unsubscribe))
                    .subscribeOn(Schedulers.io())
        }
    }
}