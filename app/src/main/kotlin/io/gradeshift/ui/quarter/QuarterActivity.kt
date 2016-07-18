package io.gradeshift.ui.quarter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.gradeshift.ui.period.PeriodFragment
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView

class QuarterActivity : AppCompatActivity() {
    lateinit var ui: QuarterUI

    companion object {
        const val EXTRA_QUARTER_NAME: String = "QUARTER_NAME" // TODO replace with an ID, maybe.
        const val EXTRA_SELECTED_CLASS_ID: String = "CLASS_ID"

        fun intent(context: Context, selectedClassId: Int, quarterName: String): Intent {
            return context.intentFor<QuarterActivity>(
                    EXTRA_QUARTER_NAME to quarterName,
                    EXTRA_SELECTED_CLASS_ID to selectedClassId
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val quarterName = intent.getStringExtra(EXTRA_QUARTER_NAME)
        val selectedClassId = intent.getIntExtra(EXTRA_SELECTED_CLASS_ID, -1)

        ui = QuarterUI()
        ui.setContentView(this)

        // Toolbar
        setSupportActionBar(ui.toolbar)
        supportActionBar?.title = quarterName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ViewPager
        val adapter = ViewPagerAdapter(supportFragmentManager, selectedClassId)
        ui.viewPager.adapter = adapter

        // TabLayout
        ui.tabLayout.setupWithViewPager(ui.viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class ViewPagerAdapter(
            // TODO this would be where we pass that QuarterID that we surely need
            fm: FragmentManager,
            val selectedClassId: Int
    ) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // TODO create fragments only once
            return PeriodFragment.newInstance(selectedClassId)
        }

        override fun getCount(): Int = 8

        override fun getPageTitle(position: Int): CharSequence {
            return "Tab $position"
        }
    }
}
