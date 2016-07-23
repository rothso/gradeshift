package io.gradeshift.ui.quarter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.gradeshift.GradesApplication
import io.gradeshift.domain.model.Course
import io.gradeshift.domain.model.CourseParcel
import io.gradeshift.domain.model.Quarter
import io.gradeshift.domain.model.QuarterParcel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import javax.inject.Inject

class QuarterActivity : AppCompatActivity() {
    @Inject lateinit var ui: QuarterUI
    private lateinit var graph: QuarterComponent

    val component: QuarterComponent
        get() = graph

    companion object {
        private const val COURSES: String = "COURSES"
        private const val QUARTER: String = "SELECTED_QUARTER"
        private const val SELECTED_COURSE_INDEX: String = "SELECTED_COURSE"

        fun intent(ctx: Context, courses: List<Course>, ofQuarter: Quarter, selectedCourseIndex: Int): Intent {
            return ctx.intentFor<QuarterActivity>(
                    COURSES to courses.map { CourseParcel(it) },
                    QUARTER to QuarterParcel(ofQuarter),
                    SELECTED_COURSE_INDEX to selectedCourseIndex
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val quarter = intent.getParcelableExtra<QuarterParcel>(QUARTER).data
        val courses = intent.getParcelableArrayListExtra<CourseParcel>(COURSES).map { it.data }
        val selectedCourseIndex = intent.getIntExtra(SELECTED_COURSE_INDEX, -1)

        graph = GradesApplication.graph.plus(QuarterModule(Quarter.DUMMY_QUARTER))
        graph.inject(this)
        ui.setContentView(this)

        // Toolbar
        setSupportActionBar(ui.toolbar)
        supportActionBar?.title = quarter.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ViewPager
        val adapter = CoursePagerAdapter(this, courses)
        ui.viewPager.adapter = adapter
        ui.viewPager.currentItem = selectedCourseIndex

        // TabLayout
        ui.tabLayout.setupWithViewPager(ui.viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}