package com.mads2202.android_1_hw_2.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


class FsImpl : FsInterface {
    companion object {
        const val NOTES = "NOTES"
        const val TAG = "FsImpl"
        var notes = mutableMapOf<String, Note>()
    }

    var db = FirebaseFirestore.getInstance()
    override fun addNote(note: Note) {
        notes.clear()
        notes.put(note.id.toString(), note)
        db.collection(NOTES).add(notes).addOnSuccessListener { documentReference ->
            Log.d(
                TAG,
                "DocumentSnapshot added with ID: " + documentReference.getId()
            );
        }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e); };
    }

    override fun deleteNote(note: Note) {
        db.collection(NOTES).document(note.id.toString()).delete()
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot successfully deleted!"
                );
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e); };
    }

    override fun clearAll() {
        db.collection(NOTES).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    document.reference.delete()
                }
            }
        }
    }

    override fun getAll() {
        db.collection(NOTES).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                notes.clear()
                task.result?.forEach {
                    var doc = it.data
                    Log.d(
                        TAG,
                        "DocumentSnapshot successfully getted!"
                    );
                }


            }
        }
    }


}