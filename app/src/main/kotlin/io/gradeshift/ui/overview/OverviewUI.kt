package io.gradeshift.ui.overview

import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import io.gradeshift.R
import io.gradeshift.model.Class
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class OverviewUI @Inject constructor(val overviewAdapter: OverviewAdapter):
        AnkoComponent<OverviewActivity>, OverviewPresenter.View {

    override fun createView(ui: AnkoContext<OverviewActivity>) = with(ui) {
        frameLayout() {
            lparams(width = matchParent, height = matchParent)
            // TODO wrap RecyclerView, error view in SwipeRefreshLayout
            recyclerView {
                id = R.id.grades_overview_list
                lparams(width = matchParent, height = matchParent)
                layoutManager = LinearLayoutManager(ctx)
                adapter = overviewAdapter
                setHasFixedSize(true) // All views are the same height and width
            }
            // TODO error view
        }
    }

    override fun showClasses(classes: List<Class>) {
        overviewAdapter.setClasses(classes)
    }

    class Item : AnkoComponent<ViewGroup> {

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
                    id = R.id.grades_overview_item_name
                    singleLine = true
                    ellipsize = TextUtils.TruncateAt.END
                    textSize = 16f
                }

                textView {
                    lparams(width = dip(40)) {
                        gravity = Gravity.CENTER_VERTICAL
                        marginStart = dip(8)
                    }
                    id = R.id.grades_overview_item_score
                    textSize = 16f
                    typeface = Typeface.create(typeface, Typeface.BOLD)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }
        }
    }
}
