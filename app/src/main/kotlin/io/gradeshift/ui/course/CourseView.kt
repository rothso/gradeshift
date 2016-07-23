package io.gradeshift.ui.course

import android.content.Context
import android.widget.LinearLayout
import org.jetbrains.anko.AnkoContext
import rx.Subscription
import javax.inject.Inject

class CourseView(context: Context) : LinearLayout(context) {

    @Inject lateinit var ui: CourseUI
    @Inject lateinit var presenter: CoursePresenter

    lateinit var subscription: Subscription

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        ui.createView(AnkoContext.createDelegate(this))
        subscription = presenter.bind(ui)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscription.unsubscribe()
    }
}