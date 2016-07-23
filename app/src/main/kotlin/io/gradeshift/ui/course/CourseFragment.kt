package io.gradeshift.ui.course

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.gradeshift.domain.model.Course
import io.gradeshift.ui.quarter.QuarterComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import rx.Subscription
import javax.inject.Inject

class CourseFragment : Fragment() {
    @Inject lateinit var ui: CourseUI
    @Inject lateinit var presenter: CoursePresenter
    lateinit var subscription: Subscription

    companion object {
        fun newInstance(course: Course, quarterComponent: QuarterComponent): CourseFragment {
            return CourseFragment().apply {
                // TODO move this inside constructor, handle rotation
                DaggerCourseComponent.builder()
                        .courseModule(CourseModule(course))
                        .quarterComponent(quarterComponent)
                        .build().inject(this)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = ui.createView(AnkoContext.create(ctx, this))
        subscription = presenter.bind(ui)
        return view
    }

    override fun onDestroyView() {
        subscription.unsubscribe()
        super.onDestroyView()
    }
}
