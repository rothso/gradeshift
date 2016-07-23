package io.gradeshift.ui.course

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.CourseParcel
import io.gradeshift.ui.quarter.QuarterActivity
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import rx.Subscription
import javax.inject.Inject

class CourseFragment() : Fragment() {
    @Inject lateinit var ui: CourseUI
    @Inject lateinit var presenter: CoursePresenter
    lateinit var subscription: Subscription

    companion object {
        private const val ARG_COURSE = "course"

        fun newInstance(course: Course): CourseFragment {
            return CourseFragment().apply {
                arguments = Bundle().apply { putParcelable(ARG_COURSE, CourseParcel(course)) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parentActivity = activity
        if (parentActivity is QuarterActivity) {
            val course = arguments.getParcelable<CourseParcel>(ARG_COURSE).data
            DaggerCourseComponent.builder()
                    .courseModule(CourseModule(course))
                    .quarterComponent(parentActivity.component) // not yet initialized after rotation
                    .build().inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = ui.createView(AnkoContext.create(ctx, this))
        subscription = presenter.bind(ui)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        subscription.unsubscribe()
        super.onDestroyView()
    }
}
