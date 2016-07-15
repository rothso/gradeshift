package io.gradeshift.ui.overview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.gradeshift.R
import io.gradeshift.model.Class
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class OverviewAdapter : RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    var classesList: List<Class> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OverviewUI.Item().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grade = classesList[position]

        holder.name.text = grade.name
        holder.score.text = grade.grade.toString() + "%"
    }

    override fun getItemCount(): Int {
        return classesList.size
    }

    fun setClasses(classesList: List<Class>) {
        this.classesList = classesList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.find<TextView>(R.id.grades_overview_item_name)
        val score = itemView.find<TextView>(R.id.grades_overview_item_score)
    }
}