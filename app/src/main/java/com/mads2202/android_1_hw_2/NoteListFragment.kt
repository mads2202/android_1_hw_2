package com.mads2202.android_1_hw_2


import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mads2202.android_1_hw_2.model.Note
import com.mads2202.android_1_hw_2.model.NoteLab
import com.mads2202.android_1_hw_2.model.SingleNoteFragment
import java.util.*


class NoteListFragment : Fragment() {
    lateinit var mRecyclerView: RecyclerView
    lateinit var callback: Callback
    private var adapter = MyAdapter(NoteLab.noteList)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)
        mRecyclerView = view.findViewById(R.id.recycler_view_note_list)
        mRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        mRecyclerView.adapter = adapter
        registerForContextMenu(mRecyclerView)
        return view
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        var titleTextView: TextView
        var dateTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.text_title)
            dateTextView = itemView.findViewById(R.id.text_date)
            itemView.setOnCreateContextMenuListener(this);

        }

        fun bind(note: Note) {
            titleTextView.text = note.title
            dateTextView.text = note.creationDate.toString()
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val inflater = activity?.menuInflater
            inflater?.inflate(R.menu.context_menu, menu)

        }


    }

    inner class MyAdapter(var noteList: List<Note>) : RecyclerView.Adapter<MyHolder>() {
        var mPosition: Int = 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            return MyHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_note,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.bind(noteList[position])
            holder.itemView.setOnClickListener {
                callback.onNoteSelected(noteList[position].id)
                mPosition = position
            }
            holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    mPosition = position
                    v?.showContextMenu()
                    return true
                }

            })


        }

        override fun getItemCount(): Int {
            return noteList.size
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                var note = Note("title", "description")
                if (activity?.findViewById<View>(R.id.detail_fragment_container) == null) {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(
                        R.id.fragments_container, SingleNoteFragment.newInstance(
                            note.id
                        )
                    )?.addToBackStack(null)?.commit()
                } else {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(
                        R.id.detail_fragment_container, SingleNoteFragment.newInstance(
                            note.id
                        )
                    )?.commit()
                }
                NoteLab.noteList.add(note)
                adapter.notifyItemInserted(NoteLab.noteList.size - 1)
            }
            R.id.action_delete -> {
                NoteLab.noteList.removeAt(adapter.mPosition)
                adapter.notifyItemRemoved(adapter.mPosition)
            }
        }
        return true
    }


}