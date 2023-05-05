package com.annaginagili.reminder

class Reminds {
    var head: String? = null
    var info: String? = null
    var date: String? = null

    companion object {
        fun getData(heads: ArrayList<String>, infos: ArrayList<String>, dates: ArrayList<String>): ArrayList<Reminds> {
            val remindList = ArrayList<Reminds>()
            for (i in 0 until heads.size) {
                val remind = Reminds()
                remind.head = heads[i]
                remind.info = infos[i]
                remind.date = dates[i]
                remindList.add(remind)
            }
            return remindList
        }
    }
}