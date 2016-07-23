package io.gradeshift.ui.quarter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import io.gradeshift.domain.model.Course
import io.gradeshift.ui.course.CourseModule
import io.gradeshift.ui.course.CourseView
import io.gradeshift.ui.course.DaggerCourseComponent

class CoursePagerAdapter(
        private val context: Context,
        private val courses: List<Course>
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return CourseView(context).apply {
            DaggerCourseComponent.builder()
                    .courseModule(CourseModule(courses[position]))
                    .quarterComponent((context as QuarterActivity).component)
                    .build().inject(this)
            container.addView(this)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getCount(): Int = courses.size
    override fun isViewFromObject(view: View, obj: Any?): Boolean = view == obj
    override fun getPageTitle(position: Int): CharSequence = courses[position].name
}
