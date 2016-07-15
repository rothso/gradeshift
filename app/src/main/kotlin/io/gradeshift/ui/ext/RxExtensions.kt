package io.gradeshift.ui.ext

import rx.Subscription
import rx.subscriptions.CompositeSubscription

operator fun CompositeSubscription.plusAssign(subscription: Subscription) = this.add(subscription)