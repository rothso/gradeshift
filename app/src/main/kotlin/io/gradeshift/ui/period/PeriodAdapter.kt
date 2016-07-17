package io.gradeshift.ui.period

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.gradeshift.R
import io.gradeshift.model.Grade
import io.gradeshift.ui.ext.ItemPressListener
import io.gradeshift.ui.ext.withItemPressListener
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import javax.inject.Inject

class PeriodAdapter @Inject constructor(val listener: ItemPressListener) : RecyclerView.Adapter<PeriodAdapter.ViewHolder>() {

    var grades: List<Grade> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PeriodUI.Item().createView(AnkoContext.create(parent.context, parent)))
                .withItemPressListener(listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grade = grades[position]
        val score = with(grade) { pointsEarned.toDouble() / pointsPossible * 100 }.toInt() // TODO calculate elsewhere

        holder.name.text = grade.name
        holder.pointsEarned.text = grade.pointsEarned.toString()
        holder.pointsPossible.text = grade.pointsPossible.toString()
        holder.score.text = score.toString() + "%"
    }

    override fun getItemCount(): Int {
        return grades.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.find<TextView>(R.id.period_class_grade_name)
        val pointsEarned = view.find<TextView>(R.id.period_class_grade_points_earned)
        val pointsPossible = view.find<TextView>(R.id.period_class_grade_points_possible)
        val score = view.find<TextView>(R.id.period_grade_score)
    }
}