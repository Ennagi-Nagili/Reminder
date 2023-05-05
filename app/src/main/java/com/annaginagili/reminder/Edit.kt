package com.annaginagili.reminder

import android.R
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.annaginagili.reminder.databinding.ActivityEditBinding

class Edit : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    lateinit var head: EditText
    lateinit var info: EditText
    lateinit var year: Spinner
    lateinit var month: Spinner
    lateinit var day: Spinner
    lateinit var hour: Spinner
    lateinit var minute: Spinner
    lateinit var save: Button
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        head = binding.head
        info = binding.info
        year = binding.year
        month = binding.month
        day = binding.day
        hour = binding.hour
        minute = binding.minute
        save = binding.save
        editor = getSharedPreferences("Data", MODE_PRIVATE).edit()

        head.setText(intent.getStringExtra("head"))
        info.setText(intent.getStringExtra("info"))

        val yearList = mutableListOf("2023", "2024", "2025", "2026", "2027")
        val monthList = mutableListOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
        val dayList = mutableListOf<String>()
        for (i in 1..31) {
            if (i < 10) {
                dayList.add("0$i")
            }

            else {
                dayList.add(i.toString())
            }
        }
        val yearAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, yearList)
        val monthAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, monthList)
        val dayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, dayList)

        year.adapter = yearAdapter
        month.adapter = monthAdapter
        day.adapter = dayAdapter

        val hourList = mutableListOf<String>()
        for (i in 0..23) {
            if (i < 10) {
                hourList.add("0$i")
            }
            else {
                hourList.add(i.toString())
            }
        }
        val minuteList = mutableListOf<String>()
        for (i in 0..59) {
            if (i < 10) {
                minuteList.add("0$i")
            }
            else {
                minuteList.add(i.toString())
            }
        }

        val hourAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, hourList)
        val minuteAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, minuteList)

        hour.adapter = hourAdapter
        minute.adapter = minuteAdapter

        var date = intent.getStringExtra("date")!!.split(" ")
        val time = date[1].split(":")
        date = date[0].split(".")

        for (i in yearList.indices) {
            if (yearList[i] == date[2]) {
                year.setSelection(i)
            }
        }

        for (i in monthList.indices) {
            if (monthList[i] == date[1]) {
                month.setSelection(i)
            }
        }

        for (i in dayList.indices) {
            if (dayList[i] == date[0]) {
                day.setSelection(i)
            }
        }

        for (i in hourList.indices) {
            if (hourList[i] == time[0]) {
                hour.setSelection(i)
            }
        }

        for (i in minuteList.indices) {
            if (minuteList[i] == time[1]) {
                minute.setSelection(i)
            }
        }

        save.setOnClickListener {
            val dateText = day.selectedItem.toString() + "." + month.selectedItem.toString() + "." +
                    year.selectedItem.toString() + " " + hour.selectedItem.toString() + ":" + minute.selectedItem.toString()
            editor.putString("head"+intent.getIntExtra("count", 0), head.text.toString())
            editor.putString("info"+intent.getIntExtra("count", 0), info.text.toString())
            editor.putString("date"+intent.getIntExtra("count", 0),dateText)
            editor.apply()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}