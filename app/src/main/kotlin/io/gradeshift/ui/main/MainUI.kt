package io.gradeshift.ui.main

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.widget.Button
import io.gradeshift.R
import org.jetbrains.anko.*
import javax.inject.Inject

class MainUI @Inject constructor(val presenter: MainPresenter) :
        AnkoComponent<MainActivity>, MainPresenter.View {

    lateinit var snackbarTrigger: Button

    init {
        presenter.attachView(this)
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            padding = dip(32)
            backgroundColor = ContextCompat.getColor(ctx, R.color.colorPrimary)

            snackbarTrigger = button {
                textResource = R.string.view_grades
                onClick { presenter.fetchGrades() }
            }.lparams { centerInParent() }
        }
    }

    override fun showGrades(grades: List<Grade>) {
        Snackbar.make(snackbarTrigger, "Hello snacko!", Snackbar.LENGTH_SHORT).show()
    }

}
