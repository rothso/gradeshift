package io.gradeshift.ui.common.ext

import android.support.v7.widget.RecyclerView

interface ItemPressListener {
    fun onItemPress(position: Int)
}

fun <H : RecyclerView.ViewHolder> H.withItemPressListener(listener: ItemPressListener): H {
    itemView.setOnClickListener {
        listener.onItemPress(adapterPosition)
    }
    return this
}