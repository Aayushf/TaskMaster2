package com.taskmaster.aayushf.taskmaster

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import io.realm.Realm

/**
 * Created by aayushf on 22/6/17.
 */
class Notifier : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val pk = intent?.getIntExtra("taskpk", 2)
        Log.d("Serviuce", "Service Started")
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val i: Intent = Intent(context, MainActivity::class.java)
        i.putExtra("primknotif", 0)
        Realm.init(context)
        val r = Realm.getDefaultInstance()
        i.putExtra("taskprimk", pk)
        val t = r.where(Task::class.java).equalTo("primk", pk).findFirst()
        val editAction: PendingIntent = PendingIntent.getActivity(context, 1, Intent(context, AdderActivity::class.java).putExtra("task", t.primk), 0)
        var doneaction: PendingIntent = PendingIntent.getActivity(context, 0, i, 0)
        val b = NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_check_circle_black_24dp).setContentTitle(t.task).setContentText(t.tag).addAction(R.drawable.ic_check_circle_black_24dp, "Done", doneaction).addAction(R.drawable.ic_mode_edit_black_24dp, "Edit", editAction).build()
        manager.notify(1000, b)
    }

}