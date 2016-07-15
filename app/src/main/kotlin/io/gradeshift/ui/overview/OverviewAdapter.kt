package io.gradeshift.ui.overview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.gradeshift.R
import io.gradeshift.model.Grade
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class OverviewAdapter(val gradesList: List<Grade>) : RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OverviewUI.Item().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grade = gradesList[position]

        holder.name.text = grade.name
        holder.score.text = grade.score.toString() + "%"
    }

    override fun getItemCount(): Int {
        return gradesList.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.find<TextView>(R.id.grades_overview_item_name)
        val score = itemView.find<TextView>(R.id.grades_overview_item_score)
    }
}