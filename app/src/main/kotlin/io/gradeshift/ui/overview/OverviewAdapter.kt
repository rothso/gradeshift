package io.gradeshift.ui.overview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.gradeshift.domain.model.Course
import io.gradeshift.ui.common.ItemPressListener
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
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
}
