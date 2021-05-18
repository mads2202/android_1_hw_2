package com.mads2202.android_1_hw_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mads2202.android_1_hw_2.model.Note
import com.mads2202.android_1_hw_2.model.SingleNoteFragment
import java.util.*

class MainActivity : AppCompatActivity(),Callback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragments_container,NoteListFragment()).commit()
    }

    override fun onNoteSelected(id: UUID){
        if(findViewById<View>(R.id.detail_fragment_container)==null){
            supportFragmentManager.beginTransaction().replace(R.id.fragments_container,SingleNoteFragment.newInstance(id)).commit()
        }else{
            supportFragmentManager.beginTransaction().replace(R.id.detail_fragment_container,SingleNoteFragment.newInstance(id)).commit()

        }

    }
}