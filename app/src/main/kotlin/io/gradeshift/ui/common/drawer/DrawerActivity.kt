package io.gradeshift.ui.common.drawer

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.*
import io.gradeshift.R
import io.gradeshift.ui.common.ext.snackBar
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView

abstract class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var ui: DrawerUI
    private lateinit var toggle: ActionBarDrawerToggle

    private var parentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = DrawerUI()
        parentView = ui.setContentView(this)

        // Toolbar
        setSupportActionBar(ui.toolbar)

        // Navigation Drawer
        val openDesc = R.string.desc_navigation_drawer_open
        val closeDesc = R.string.desc_navigation_drawer_close
        toggle = ActionBarDrawerToggle(this, ui.drawer, ui.toolbar, openDesc, closeDesc)
        ui.drawer.addDrawerListener(toggle)
        toggle.syncState()

        // Navigation Guts
        ui.navigationView.setNavigationItemSelectedListener(this)
        // TODO dagger inject
    }

    override fun setContentView(view: View) {
        if (parentView == null) {
            super.setContentView(view)
        } else {
            parentView?.find<ViewGroup>(ContentUI.CONTENT_FRAME_ID)?.addView(view)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.temp, menu)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle, persistentState: PersistableBundle) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (ui.drawer.isDrawerOpen(GravityCompat.START)) {
            ui.drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> snackBar("Camera")
            R.id.nav_gallery -> snackBar("Gallery")
            R.id.nav_slideshow -> snackBar("Slideshow")
            R.id.nav_manage -> snackBar("Manage")
            R.id.nav_share -> snackBar("Share")
            R.id.nav_send -> snackBar("Send")
        }

        ui.drawer.closeDrawer(Gravity.START)
        return true
    }
}