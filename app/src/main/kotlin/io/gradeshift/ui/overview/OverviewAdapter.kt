package io.gradeshift.ui.overview

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.gradeshift.domain.model.Course
import io.gradeshift.ui.common.ItemPressListener
import org.jetbrains.anko.*
import javax.inject.Inject

class OverviewAdapter @Inject constructor(val listener: ItemPressListener) : RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    var courses: List<Course> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OverviewItemUI().createView(AnkoContext.createDelegate(parent))).apply {
            itemView.setOnClickListener { listener.onItemPress(adapterPosition) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grade = courses[position]

        holder.name.text = grade.name
        holder.teacher.text = grade.teacher
        holder.score.text = grade.grade.toString()
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.find<TextView>(OverviewItemUI.ID_COURSE_NAME)
        val score = view.find<TextView>(OverviewItemUI.ID_COURSE_GRADE)
        val teacher = view.find<TextView>(OverviewItemUI.ID_COURSE_TEACHER)
    }

    private class OverviewItemUI : AnkoComponent<ViewGroup> {

        companion object {
            val ID_COURSE_NAME: Int = View.generateViewId()
            val ID_COURSE_GRADE: Int = View.generateViewId()
            val ID_COURSE_TEACHER: Int = View.generateViewId()
        }

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.HORIZONTAL
                padding = dip(16)

                linearLayout {
                    orientation = LinearLayout.VERTICAL

                    textView {
                        id = ID_COURSE_NAME
                        singleLine = true
                        ellipsize = TextUtils.TruncateAt.END
                        textSize = 16f
                        textColor = Color.BLACK.withAlpha(216)
                        typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    }.lparams {
                        gravity = Gravity.CENTER_VERTICAL
                        weight = 1.0f
                    }

                    textView {
                        id = ID_COURSE_TEACHER
                        singleLine = true
                        ellipsize = TextUtils.TruncateAt.END
                        textSize = 16f
                        textColor = Color.LTGRAY
                    }.lparams {
                        gravity = Gravity.CENTER_VERTICAL
                        weight = 1.0f
                    }
                }.lparams {
                    gravity = Gravity.CENTER_VERTICAL
                    weight = 1.0f
                }

                textView {
                    lparams(width = wrapContent) {
                        gravity = Gravity.CENTER_VERTICAL
                        marginStart = dip(8)
                    }
                    id = ID_COURSE_GRADE
                    gravity = Gravity.CENTER_HORIZONTAL
                    textSize = 24f
                    textColor = Color.BLACK.withAlpha(216)
                    typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
                }
            }
        }
    }
}
