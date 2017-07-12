package com.taskmaster.aayushf.taskmaster

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.db.chart.model.BarSet
import com.db.chart.model.ChartEntry
import com.db.chart.view.ChartView
import kotlinx.android.synthetic.main.activity_stats.*


class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var tags = Task.getAllTagsPoints()
        var fas: MutableList<Float> = arrayListOf()
        var labels: MutableList<String> = arrayListOf<String>()
        tags.forEach { t: Pair<String, Int>? ->
            labels.add(t?.first!!)
            fas.add(t?.second?.toFloat()!!)

        }

        var fa: FloatArray = kotlin.FloatArray(labels.size, { i: Int ->
            fas[i]

        })
        var labelsarray = labels.toTypedArray()
        var bs: BarSet = BarSet(labelsarray, fa)
        bs.color = Color.parseColor("#fc2a53")

        barchart.addData(bs)
        barchart.show()
    }
}
