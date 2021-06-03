package com.mads2202.android_1_hw_2.model

import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


object NoteLab {

    fun getNote(id: UUID): Note? {
        for (item in noteList) {
            if (item.id == id) {
                return item
            }
        }
        return null
    }

    fun updateCrime(note: Note) {
        getNote(note.id)?.creationDate = note.creationDate

    }

    var noteList = mutableListOf<Note>()

    init {
        for (i in 0..100) {
            noteList.add(Note("title $i", "description $i"))
        }
    }
}