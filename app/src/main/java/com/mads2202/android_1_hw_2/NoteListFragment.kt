package com.mads2202.android_1_hw_2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.android_1_hw_2.model.Note
import com.mads2202.android_1_hw_2.model.NoteLab
import com.mads2202.android_1_hw_2.model.SingleNoteFragment

class NoteListFragment : Fragment() {
    lateinit var mRecyclerView: RecyclerView
    lateinit var callback: Callback
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)
        mRecyclerView = view.findViewById(R.id.recycler_view_note_list)
        mRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = MyAdapter(NoteLab.noteList)
        return view
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView
        var dateTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.text_title)
            dateTextView = itemView.findViewById(R.id.text_date)
        }

        fun bind(note: Note) {
            titleTextView.text = note.title
            dateTextView.text = note.creationDate.toString()
        }

    }

    inner class MyAdapter(var noteList: List<Note>) : RecyclerView.Adapter<MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_note, parent, false))
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.bind(noteList[position])
            holder.itemView.setOnClickListener {
                callback.onNoteSelected(noteList[position].id)
            }

        }

        override fun getItemCount(): Int {
            return noteList.size
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback
    }


}