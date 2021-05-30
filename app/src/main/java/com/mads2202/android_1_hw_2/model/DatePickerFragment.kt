package com.mads2202.android_1_hw_2.model

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.mads2202.android_1_hw_2.R
import java.util.*

class DatePickerFragment : DialogFragment() {
    companion object {
        const val DATE = "Date"
        const val EXTRA_DATE = "ExtraDate"
        fun newInstance(date: Date): DialogFragment {
            var args = Bundle()
            args.putSerializable(DATE, date)
            var datePickerFragment = DatePickerFragment()
            datePickerFragment.arguments = args
            return datePickerFragment

        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var date = arguments?.getSerializable(DATE) as Date
        var calendar = Calendar.getInstance()
        calendar.time = date
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_date_picker, null)
        var datePicker: DatePicker = view.findViewById(R.id.dialog_date_picker)
        datePicker.init(year, month, day, null)

        return AlertDialog.Builder(activity).setTitle(resources.getString(R.string.choose_the_date))
                .setPositiveButton(resources.getString(R.string.ok), object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        var year = datePicker.year
                        var month = datePicker.month
                        var day = datePicker.dayOfMonth
                        var date = GregorianCalendar(year, month, day).time
                        sendResult(Activity.RESULT_OK, date)
                    }
                }).setView(view)
                .create()
    }

    fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null)
            return
        else {
            var intent = Intent()
            intent.putExtra(EXTRA_DATE, date)
            targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
        }
    }

}