package io.gradeshift.ui.overview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.gradeshift.GradesApplication
import org.jetbrains.anko.setContentView
import javax.inject.Inject

class OverviewActivity : AppCompatActivity() {
    @Inject lateinit var ui: OverviewUI
    @Inject lateinit var presenter: OverviewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GradesApplication.graph.plus(OverviewModule()).inject(this)
        ui.setContentView(this)
        presenter.attachView(ui)
    }
}
