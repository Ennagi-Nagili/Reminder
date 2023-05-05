package com.annaginagili.reminder

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.annaginagili.reminder.databinding.ActivityAddBinding

class Add : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    lateinit var year: Spinner
    lateinit var month: Spinner
    lateinit var day: Spinner
    lateinit var hour: Spinner
    lateinit var minute: Spinner
    lateinit var head: EditText
    lateinit var info: EditText
    lateinit var add: Button
    lateinit var preferences: SharedPreferences
    lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        year = binding.year
        month = binding.month
        day = binding.day
        hour = binding.hour
        minute = binding.minute
        head = binding.head
        info = binding.info
        add = binding.add
        preferences = getSharedPreferences("Data", MODE_PRIVATE)
        editor = preferences.edit()

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
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList)
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monthList)
        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dayList)

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

        val hourAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hourList)
        val minuteAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, minuteList)

        hour.adapter = hourAdapter
        minute.adapter = minuteAdapter

        add.setOnClickListener {
            if (head.text.isNotEmpty() && info.text.isNotEmpty()) {
                val count = preferences.getInt("count", 0)
                editor.putString("date"+ (count+1).toString(), day.selectedItem.toString() + "." +
                        month.selectedItem.toString() + "." + year.selectedItem.toString() + " "
                        + hour.selectedItem.toString() + ":" + minute.selectedItem.toString())
                editor.putString("head"+ (count+1).toString(), head.text.toString())
                editor.putString("info"+ (count+1).toString(), info.text.toString())
                editor.putInt("count", count+1)
                editor.apply()
                startActivity(Intent(this, MainActivity::class.java))
            }

            else {
                Toast.makeText(this, "Fill head and info", Toast.LENGTH_SHORT).show()
            }
        }
    }
}