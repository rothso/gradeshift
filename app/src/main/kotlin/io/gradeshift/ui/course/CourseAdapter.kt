package io.gradeshift.ui.course

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.gradeshift.R
import io.gradeshift.domain.model.Grade
import io.gradeshift.ui.common.ItemPressListener
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import javax.inject.Inject

class CourseAdapter @Inject constructor(val listener: ItemPressListener) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    var grades: List<Grade> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CourseUI.Item().createView(AnkoContext.create(parent.context, parent))).apply {
            itemView.setOnClickListener { listener.onItemPress(adapterPosition) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grade = grades[position]
        val score = grade.score.toInt()

        holder.name.text = grade.name
        holder.pointsEarned.text = grade.pointsEarned.toString()
        holder.pointsPossible.text = grade.pointsPossible.toString()
        holder.score.text = score.toString() + "%"
    }

    override fun getItemCount(): Int {
        return grades.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.find<TextView>(R.id.course_grade_name)
        val pointsEarned = view.find<TextView>(R.id.course_grade_points_earned)
        val pointsPossible = view.find<TextView>(R.id.course_grade_points_possible)
        val score = view.find<TextView>(R.id.course_grade_score)
    }
}