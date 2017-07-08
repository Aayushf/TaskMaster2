package com.taskmaster.aayushf.taskmaster

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by aayushf on 10/6/17.
 */
open class Task(var task: String = "Not Specified", var tag: String? = null, var colour: Int = 1, var done: Boolean = false, var points: Int = 25, var dateadded: Long = Calendar.getInstance().timeInMillis, var datepending: Long? = dateadded + 1000 * 60 * 60 * 24 * 3, var datedone: Long? = null) : RealmObject() {
    @PrimaryKey
    var primk: Int = 0
        get
        set

    fun toggleDone() {
        if (this.done) {
            this.done = false
            this.datedone = null
        } else {
            this.done = true
            this.datedone = Calendar.getInstance().timeInMillis
        }
    }
    companion object {
        val coloursnames = listOf("White", "Tomato", "Tangerine", "Banana", "Basil", "Sage", "Peacock", "Blueberry", "Lavender", "Grape", "Flamingo", "Graphite")
        val quotes = listOf("It's Not Over Until You Succeed.", "The Fact That You Are Not Where You Want To Be Should Be Enough Inspiration.")
        var colours = listOf(0xFF000000.toInt(), 0xFFD50000.toInt(), 0xFFF4511E.toInt(), 0xFFF6BF26.toInt(), 0xFF0B8043.toInt(), 0xFF33B679.toInt(), 0xFF039BE5.toInt(), 0xFF3F51B5.toInt(), 0xFF7986CB.toInt(), 0xFF8E24AA.toInt(), 0xFFE67C73.toInt(), 0xFF616161.toInt())
    }


}
