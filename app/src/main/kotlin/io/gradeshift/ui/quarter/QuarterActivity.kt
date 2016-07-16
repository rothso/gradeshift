package io.gradeshift.ui.quarter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView

class QuarterActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_CLASS_ID: String = "CLASS_ID"

        fun intent(context: Context, classId: Int): Intent {
            return context.intentFor<QuarterActivity>(EXTRA_CLASS_ID to classId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val classId = intent.getIntExtra(EXTRA_CLASS_ID, -1)
        QuarterUI(classId).setContentView(this)
    }
}
