package io.gradeshift.ui.overview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.gradeshift.R
import io.gradeshift.model.Class
import io.gradeshift.ui.common.ext.ItemPressListener
import io.gradeshift.ui.common.ext.withItemPressListener
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import javax.inject.Inject

class OverviewAdapter @Inject constructor(val listener: ItemPressListener) : RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    var classes: List<Class> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OverviewUI.Item().createView(AnkoContext.create(parent.context, parent)))
                .withItemPressListener(listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grade = classes[position]

        holder.name.text = grade.name
        holder.score.text = grade.grade.toString() + "%"
    }

    override fun getItemCount(): Int {
        return classes.size
    }

    fun getItem(position: Int): Class {
        return classes[position]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.find<TextView>(R.id.grades_overview_class_name)
        val score = view.find<TextView>(R.id.grades_overview_class_score)
    }
}
