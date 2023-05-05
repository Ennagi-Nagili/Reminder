package com.annaginagili.reminder

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.annaginagili.reminder.databinding.ReminderLayoutBinding
import java.util.Scanner

class RemindAdapter(private val context: Context, private val remindList: ArrayList<Reminds>) :
    RecyclerView.Adapter<RemindAdapter.RemindHolder>() {
    private var listener: OnClickListener? = null

    class RemindHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ReminderLayoutBinding.bind(itemView)
        private val head = binding.head
        private val time = binding.time
        fun setData(context:Context, reminds: Reminds, remindList: ArrayList<Reminds>, listener: OnClickListener) {
            this.head.text = reminds.head
            this.time.text = reminds.date
            itemView.setOnClickListener {
                val intent1 = Intent(context, Details::class.java)
                intent1.putExtra("head", reminds.head)
                intent1.putExtra("info", reminds.info)
                intent1.putExtra("date", reminds.date)
                intent1.putExtra("position", adapterPosition+1)
                context.startActivity(intent1)
            }

            itemView.setOnLongClickListener {
                listener.onClick(adapterPosition, remindList)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.reminder_layout, parent, false)
        return RemindHolder(view)
    }

    override fun getItemCount(): Int {
        return remindList.size
    }

    override fun onBindViewHolder(holder: RemindHolder, position: Int) {
        val reminds: Reminds = remindList[position]
        holder.setData(context, reminds, remindList, listener!!)
    }

    interface OnClickListener {
        fun onClick(position: Int, remindList: ArrayList<Reminds>)
    }

    fun setOnVipClickListener(listener: OnClickListener) {
        this.listener = listener
    }
}