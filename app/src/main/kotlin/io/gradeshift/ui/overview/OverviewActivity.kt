package io.gradeshift.ui.overview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.gradeshift.GradesApplication
import io.gradeshift.R
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.singleTop
import rx.Subscription
import javax.inject.Inject

class OverviewActivity : AppCompatActivity() {
    @Inject lateinit var ui: OverviewUI
    @Inject lateinit var presenter: OverviewPresenter
    lateinit var subscription: Subscription

    companion object {
        fun intent(context: Context): Intent {
            return context.intentFor<OverviewActivity>().singleTop()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GradesApplication.graph.plus(OverviewModule(this)).inject(this)
        ui.setContentView(this)

        // Toolbar
        setSupportActionBar(ui.toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Navigation Drawer
        val openDesc = R.string.desc_navigation_drawer_open
        val closeDesc = R.string.desc_navigation_drawer_close
        val toggle = ActionBarDrawerToggle(this, ui.drawer, ui.toolbar, openDesc, closeDesc)
        ui.drawer.addDrawerListener(toggle)
        toggle.syncState()

        subscription = presenter.bind(ui)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                ui.drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (ui.drawer.isDrawerOpen(GravityCompat.START)) {
            ui.drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}
