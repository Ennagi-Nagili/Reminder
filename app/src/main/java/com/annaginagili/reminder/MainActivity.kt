package com.annaginagili.reminder

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annaginagili.reminder.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var add: Button
    lateinit var recycler: RecyclerView
    lateinit var adapter: RemindAdapter
    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        add = binding.add
        recycler = binding.recycler
        preferences = getSharedPreferences("Data", MODE_PRIVATE)
        editor = preferences.edit()

        add.setOnClickListener {
            startActivity(Intent(this, Add::class.java))
        }

        recycler.setHasFixedSize(true)
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = manager
        val count = preferences.getInt("count", 0)
        val heads = ArrayList<String>()
        val dates = ArrayList<String>()
        val infos = ArrayList<String>()
        if (count != 0) {
            for (i in 1..count) {
                heads.add(preferences.getString("head$i", "")!!)
                dates.add(preferences.getString("date$i", "")!!)
                infos.add(preferences.getString("info$i", "")!!)
            }
        }
        adapter = RemindAdapter(this, Reminds.getData( heads, infos, dates))
        recycler.adapter = adapter
        adapter.setOnVipClickListener(object : RemindAdapter.OnClickListener {
            override fun onClick(position: Int, remindList:ArrayList<Reminds>) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Are you sure delete this reminder?")
                    .setCancelable(true)
                    .setPositiveButton("Yes") { _, _ ->
                        editor.remove("head" + (position+1).toString())
                        editor.remove("info" + (position+1).toString())
                        editor.remove("date" + (position+1).toString())
                        editor.putInt("count", count-1)
                        editor.apply()

                        remindList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                        adapter.notifyItemRangeChanged(position, remindList.size)
                    }
                    .setNegativeButton("No") { dialogInterface, _ ->
                        dialogInterface.cancel()
                    }
                val alert = builder.create()
                alert.show()
            }
        })
    }

//    private fun createNotificationChannel() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val name  = "reminderChannel"
//            val description = "reminderChannel"
//            val importance = NotificationManager.IMPORTANCE_HIGH
//            val channel = NotificationChannel("channel", name, importance)
//            channel.description = description
//            val notificationManager = getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
}