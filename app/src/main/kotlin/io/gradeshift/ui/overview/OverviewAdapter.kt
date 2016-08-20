package io.gradeshift.ui.overview

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
        holder.score.text = grade.grade.toString() + "%"
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.find<TextView>(OverviewItemUI.ID_COURSE_NAME)
        val score = view.find<TextView>(OverviewItemUI.ID_COURSE_GRADE)
    }

    private class OverviewItemUI : AnkoComponent<ViewGroup> {

        companion object {
            val ID_COURSE_NAME: Int = View.generateViewId()
            val ID_COURSE_GRADE: Int = View.generateViewId()
        }

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
                    id = ID_COURSE_NAME
                    singleLine = true
                    ellipsize = TextUtils.TruncateAt.END
                    textSize = 16f
                }

                textView {
                    lparams(width = dip(40)) {
                        gravity = Gravity.CENTER_VERTICAL
                        marginStart = dip(8)
                    }
                    id = ID_COURSE_GRADE
                    textSize = 16f
                    typeface = Typeface.create(typeface, Typeface.BOLD)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }
        }
    }
}
