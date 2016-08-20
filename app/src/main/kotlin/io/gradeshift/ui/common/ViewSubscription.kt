package io.gradeshift.ui.common

import rx.Observable
import rx.Subscription

typealias ViewConsumer<T> = (Observable<T>) -> Subscription
