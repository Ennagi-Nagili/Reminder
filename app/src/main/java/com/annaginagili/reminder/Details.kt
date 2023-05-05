package com.annaginagili.reminder

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.annaginagili.reminder.databinding.ActivityDetailsBinding

class Details : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    lateinit var head: TextView
    lateinit var info: TextView
    lateinit var date: TextView
    lateinit var edit: Button
    lateinit var preferences: SharedPreferences
    lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        head = binding.head
        info = binding.info
        date = binding.date
        edit = binding.edit
        preferences = getSharedPreferences("Data", MODE_PRIVATE)
        editor = preferences.edit()

        head.text = intent.getStringExtra("head")
        info.text = intent.getStringExtra("info")
        date.text = intent.getStringExtra("date")

        edit.setOnClickListener {
            val intent1 = Intent(this, Edit::class.java)
            intent1.putExtra("head", head.text.toString())
            intent1.putExtra("info", info.text.toString())
            intent1.putExtra("date", date.text.toString())
            intent1.putExtra("count", intent.getIntExtra("position", 0))
            startActivity(intent1)
        }
    }
}