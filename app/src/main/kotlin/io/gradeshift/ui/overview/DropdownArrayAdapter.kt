package io.gradeshift.ui.overview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import org.jetbrains.anko.*

class DropdownArrayAdapter<T>(ctx: Context, private val items: List<T>) : ArrayAdapter<T>(ctx, 0, items) {
    private val ankoContext = AnkoContext.createReusable(ctx, this)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View = with(ankoContext) {
        val name = items[position].toString()
        textView(name) {
            padding = dip(16)
            textColor = Color.BLACK.withAlpha(138)
            textSize = 16f
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View = with(ankoContext) {
        val name = items[position].toString()
        textView(name) {
            padding = dip(16)
            textColor = Color.BLACK.withAlpha(138)
            textSize = 16f
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        }
    }
}