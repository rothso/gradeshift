package io.gradeshift.ui.common.ext

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.lang.kotlin.subscribeWith
import timber.log.Timber

/**
 * MIT License
 *
 * Copyright (c) [2016] [Artem Zinnatullin]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

fun <T> Observable<T>.bind(uiFunc: (Observable<T>) -> Subscription): Subscription {
    return uiFunc.invoke(this)
}

/* Maintain compatibility with Action1<*>s provided by RxBinding */
fun <T> ui(onNext: Action1<in T>): (Observable<T>) -> Subscription =
        ui { it -> onNext.call(it) }

fun <T> ui(onNext: (T) -> Unit): (Observable<T>) -> Subscription = {
    it.observeOn(AndroidSchedulers.mainThread()).subscribeWith {
        onNext(onNext)
        onError { Timber.e(it, null) }
    }
}