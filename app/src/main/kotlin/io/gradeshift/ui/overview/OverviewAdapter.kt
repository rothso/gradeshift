package io.gradeshift.ui.overview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.gradeshift.R
import io.gradeshift.domain.model.Course
import io.gradeshift.ui.common.ext.ItemPressListener
import io.gradeshift.ui.common.ext.withItemPressListener
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * TODO:
 * http://stackoverflow.com/documentation/android/169/recyclerview/690/gridlayoutmanager-with-onclicklistener-and-dynamic-dataset
 * http://stackoverflow.com/documentation/android/96/recyclerview-onclicklisteners/432/kotlin-and-rxjava-example
 */
class OverviewAdapter @Inject constructor(val listener: ItemPressListener) : RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    var courses: List<Course> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OverviewUI.Item().createView(AnkoContext.create(parent.context, parent)))
                .withItemPressListener(listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grade = courses[position]

        holder.name.text = grade.name
        holder.score.text = grade.grade.toString() + "%"
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun getItem(position: Int): Course {
        return courses[position]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.find<TextView>(R.id.grades_overview_class_name)
        val score = view.find<TextView>(R.id.grades_overview_class_score)
    }
}
