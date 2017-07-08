package com.taskmaster.aayushf.taskmaster

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.EditText
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_adder.*
import kotlinx.android.synthetic.main.content_adder.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedAlertDialogLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class AdderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adder)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        fabcolour.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()


        }

        var b: Boolean? = null
        Realm.init(this)
        val real: Realm = Realm.getDefaultInstance()
        var t: Task = Task()
        if (!real.isInTransaction) {
            real.beginTransaction()
        }


        if (intent.getStringExtra("task") != null) {
            val i: Intent = intent
            val taskstr = i.getStringExtra("task")
            val realmrecieved: Realm = Realm.getDefaultInstance()
            val recievedtask: Task = realmrecieved.where(Task::class.java).equalTo("task", taskstr).findFirst()
            ettask.setText(recievedtask.task)
            etpoints.setText(recievedtask.points.toString())
            ettag.setText(recievedtask.tag)
            t = recievedtask
            b = true
        } else {
            b = false
        }
        app_bar.bringToFront()

        operator fun EditText.unaryMinus(): String {
            return this.text.toString()
        }

        val sdf: SimpleDateFormat = SimpleDateFormat("EEE - dd MMM")

        btndateadded.setOnClickListener {
            val dpd: DatePickerDialog = DatePickerDialog(this@AdderActivity)
            dpd.setOnDateSetListener { view, year, month, dayOfMonth ->
                t.dateadded = Date(year, month, dayOfMonth).time

                btndateadded.text = "{cmd_calendar_plus} Added On ${sdf.format(Date(year, month, dayOfMonth))}"
            }
            dpd.show()

        }
        btndatedue.setOnClickListener {
            val dpd: DatePickerDialog = DatePickerDialog(this@AdderActivity)
            dpd.setOnDateSetListener { view, year, month, dayOfMonth ->
                t.datepending = Date(year, month, dayOfMonth).time
                btndatedue.text = "{cmd_calendar_plus} Added On ${sdf.format(Date(year, month, dayOfMonth))}"


            }
            dpd.show()


        }
        btnnotification.onClick {
            alert {
                var c: Calendar = Calendar.getInstance()
                themedAlertDialogLayout {
                    backgroundColor = 0x00000000
                    button {
                        text = "Date?"
                        onClick {
                            val dpd: DatePickerDialog = DatePickerDialog(this@AdderActivity)
                            dpd.setOnDateSetListener { view, year, month, dayOfMonth ->
                                c.set(Calendar.YEAR, year)
                                c.set(Calendar.MONTH, month)
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)


                            }
                            dpd.show()


                        }
                    }
                    button {
                        text = "Time?"
                        onClick {
                            val tpd = TimePickerDialog(this@AdderActivity, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                c.set(Calendar.MINUTE, minute)

                            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false)
                            tpd.show()
                        }
                    }
                    button {
                        text = "Done"
                        onClick {
                            notify(t.primk, c.timeInMillis)

                        }
                    }
                }
            }
        }


        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            t.task = -ettask
            t.tag = (ettag.text.toString())
            t.points = (-etpoints).toInt()
            Realm.init(this)
            Log.d(t.task, t.tag)
            Log.d("Adder", t.toString())
            val r: Realm = Realm.getDefaultInstance()
            var n: Number? = r.where(Task::class.java).max("primk")
            if (b == false) {
                if (n != null) {
                    t.primk = n.toInt() + 1
                } else {
                    t.primk = 0
                }
            }
            if (!real.isInTransaction) {
                real.beginTransaction()
                real.copyToRealmOrUpdate(t)
                real.commitTransaction()

            } else {
                real.copyToRealmOrUpdate(t)
                real.commitTransaction()

            }


        }
        fabcolour.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            selector("Colour", Task.coloursnames) { _, i ->

                t.colour = i


            }
        }


    }

    fun notify(taskPrimk: Int, time: Long) {
        val i = Intent(this@AdderActivity, Notifier::class.java)
        i.putExtra("taskpk", taskPrimk)

        val pi = PendingIntent.getBroadcast(this@AdderActivity, 0, i, 0)
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.RTC_WAKEUP, time, pi)
        Log.d("AdderActivity", "Notifier Called")


    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity>()

    }
}
