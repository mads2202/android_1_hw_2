package com.mads2202.android_1_hw_2


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.mads2202.android_1_hw_2.model.SingleNoteFragment
import java.util.*


class MainActivity : AppCompatActivity(), Callback {
    lateinit var drawer: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
         drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        supportFragmentManager.beginTransaction().replace(R.id.fragments_container, NoteListFragment()).commit()
    }
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }




    override fun onNoteSelected(id: UUID) {
        if (findViewById<View>(R.id.detail_fragment_container) == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragments_container, SingleNoteFragment.newInstance(id)).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.detail_fragment_container, SingleNoteFragment.newInstance(id)).commit()

        }

    }
}