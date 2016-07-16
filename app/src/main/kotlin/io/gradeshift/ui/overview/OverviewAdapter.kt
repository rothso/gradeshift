package io.gradeshift.ui.overview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.gradeshift.R
import io.gradeshift.model.Class
import io.gradeshift.ui.ext.ItemPressListener
import io.gradeshift.ui.ext.withItemPressListener
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import javax.inject.Inject

class OverviewAdapter @Inject constructor(val listener: ItemPressListener) : RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    var classesList: List<Class> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OverviewUI.Item().createView(AnkoContext.create(parent.context, parent)))
                .withItemPressListener(listener)
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.find<TextView>(R.id.grades_overview_class_name)
        val score = view.find<TextView>(R.id.grades_overview_class_score)
    }
}
