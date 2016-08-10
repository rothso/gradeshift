package io.gradeshift.data.network.google

import com.github.ajalt.timberkt.d
import com.google.android.gms.common.api.Status
import io.gradeshift.data.network.auth.exception.LoginRequiredException
import io.gradeshift.data.network.auth.exception.ResolutionRequiredException
import io.gradeshift.ui.common.ext.bind
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class GacResolution {

    interface Callback {
        val resolveStatus: (Observable<Status>) -> Subscription
        val showLogin: (Observable<Unit>) -> Subscription
    }

    companion object {
        private val NO_RETRY: Observable<Any> = Observable.empty<Any>()
        private val RETRY: Observable<Unit> = Observable.just(Unit)
                .subscribeOn(AndroidSchedulers.mainThread())

        fun <T> resolve(observable: Observable<T>, view: Callback): Observable<T> {
            tailrec fun resolve(error: Throwable): Observable<*> {
                return when (error) {
                    is LoginRequiredException -> { // TODO move
                        d { "Login required, showing login" }
                        Observable.just(Unit).bind(view.showLogin)
                        NO_RETRY
                    }
                    is ResolutionRequiredException -> {
                        Observable.just(error.status).bind(view.resolveStatus)
                        RETRY
                    }
                    else -> {
                        val cause = error.cause
                        if (error is RuntimeException && cause != null) {
                            resolve(cause)
                        } else {
                            d { "Passing error through " }
                            Observable.error<Any>(error)
                        }
                    }
                }
            }

            return observable.retryWhen {
                it.flatMap { resolve(it as Throwable) }
            }
        }
    }
}