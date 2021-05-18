package com.mads2202.android_1_hw_2.model

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.mads2202.android_1_hw_2.R
import java.util.*

class SingleNoteFragment:Fragment() {
    companion object{
        const val NOTE_ID="NoteId"
        const val REQUEST_DATE=1
        const val DIALOG_DATE="DialogDate"
        fun newInstance(id:UUID):Fragment{
            var args:Bundle= Bundle()
            args.putSerializable(NOTE_ID,id)
            val singleNoteFragment= SingleNoteFragment()
            singleNoteFragment.arguments=args
            return singleNoteFragment
        }
    }
     var mNote: Note?=null
    lateinit var mTitleEditText:EditText
    lateinit var mDescriptionEditText:EditText
    lateinit var mDateButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var id: UUID = arguments?.getSerializable(NOTE_ID) as UUID
        if(id!=null){
            mNote=NoteLab.getNote(id)
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=inflater.inflate(R.layout.fragment_single_note,container,false)
        mTitleEditText=view.findViewById(R.id.edit_text_title)
        mDescriptionEditText=view.findViewById(R.id.edit_text_description)
        mDateButton=view.findViewById(R.id.button_date)
        mNote?.let { fillElements(it) }
        return view
    }
    fun fillElements(note: Note){
        mTitleEditText.setText(note.title)
        mTitleEditText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.title=s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        mDateButton.text=note.creationDate.toString()
        mDateButton.setOnClickListener {
            val datePickerFragment=DatePickerFragment.newInstance(note.creationDate)
            datePickerFragment.setTargetFragment(this, REQUEST_DATE)
            datePickerFragment.show(fragmentManager!!, DIALOG_DATE)
        }
        mDescriptionEditText.setText(note.description)
        mDescriptionEditText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.description=s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==REQUEST_DATE){
            val date= data!!.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            mNote?.creationDate=date
            NoteLab.updateCrime(mNote!!)
            mDateButton.text = android.text.format.DateFormat.format(
                    "EEEE, dd MMMM, yyyy",
                    mNote!!.creationDate
            )
        }}

}