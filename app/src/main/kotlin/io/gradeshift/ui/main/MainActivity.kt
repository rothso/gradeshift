package io.gradeshift.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.gradeshift.GradesApplication
import org.jetbrains.anko.setContentView
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var ui: MainUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GradesApplication.graph.inject(this)
        Timber.d("Graph injected into MainActivity")
        ui.setContentView(this)
    }

}
