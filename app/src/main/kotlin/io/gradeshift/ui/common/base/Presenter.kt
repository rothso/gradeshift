package io.gradeshift.ui.common.base

import rx.Subscription
import timber.log.Timber

abstract class Presenter<in V> {

    init {
        // TODO survive orientation changes, or simply be passive?
        Timber.i("Creating presenter")
    }

    abstract fun bind(view: V): Subscription
}