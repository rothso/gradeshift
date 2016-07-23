package io.gradeshift.ui.overview

import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*

// TODO move inside Adapter, clean up
class OverviewItemUI : AnkoComponent<ViewGroup> {

    companion object {
        val ID_COURSE_NAME: Int = View.generateViewId()
        val ID_COURSE_GRADE: Int = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = dip(48))
            orientation = LinearLayout.HORIZONTAL
            horizontalPadding = dip(16)

            textView {
                lparams {
                    gravity = Gravity.CENTER_VERTICAL
                    weight = 1.0f
                }
                id = ID_COURSE_GRADE
                singleLine = true
                ellipsize = TextUtils.TruncateAt.END
                textSize = 16f
            }

            textView {
                lparams(width = dip(40)) {
                    gravity = Gravity.CENTER_VERTICAL
                    marginStart = dip(8)
                }
                id = ID_COURSE_NAME
                textSize = 16f
                typeface = Typeface.create(typeface, Typeface.BOLD)
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }
    }
}