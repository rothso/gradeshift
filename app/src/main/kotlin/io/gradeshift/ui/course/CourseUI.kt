package io.gradeshift.ui.course

import android.graphics.Typeface
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.artemzin.rxui.RxUi.ui
import io.gradeshift.R
import io.gradeshift.domain.model.Grade
import io.gradeshift.ui.common.ext.ItemPressListener
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import timber.log.Timber
import javax.inject.Provider

class CourseUI(
        val adapterProvider: Provider<CourseAdapter>
) : AnkoComponent<CourseView>, CoursePresenter.View, ItemPressListener {

    lateinit var CourseAdapter: CourseAdapter

    override val showGrades = ui<List<Grade>> { CourseAdapter.grades = it }
    override fun onItemPress(position: Int) = Timber.i("Pressed item $position")

    override fun createView(ui: AnkoContext<CourseView>) = with(ui) {
        CourseAdapter = adapterProvider.get()

        swipeRefreshLayout {
            onRefresh {
                // TODO support
                isRefreshing = true
            }

            recyclerView {
                id = R.id.grades_overview_list
                lparams(width = matchParent, height = matchParent)
                layoutManager = LinearLayoutManager(ctx)
                itemAnimator = DefaultItemAnimator()
                adapter = CourseAdapter
                setHasFixedSize(true) // All views are the same height and width
            }
        }
    }

    class Item : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = dip(48))
                orientation = LinearLayout.HORIZONTAL
                horizontalPadding = dip(16)

                textView {
                    lparams {
                        gravity = Gravity.CENTER_VERTICAL
                        weight = 1.0f
                    }
                    id = R.id.course_grade_name
                    singleLine = true
                    ellipsize = TextUtils.TruncateAt.END
                    textSize = 16f
                }

                linearLayout {
                    lparams(width = dip(80)) {
                        gravity = Gravity.CENTER_VERTICAL
                        marginStart = dip(8)
                    }
                    gravity = Gravity.CENTER_HORIZONTAL

                    textView {
                        id = R.id.course_grade_points_earned
                        textSize = 16f
                    }
                    textView("/") {
                        textSize = 16f
                    }
                    textView {
                        id = R.id.course_grade_points_possible
                        textSize = 16f
                    }
                }

                textView {
                    lparams(width = dip(40)) {
                        gravity = Gravity.CENTER_VERTICAL
                        marginStart = dip(8)
                    }
                    id = R.id.course_grade_score
                    textSize = 16f
                    typeface = Typeface.create(typeface, Typeface.BOLD)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }
        }
    }
}