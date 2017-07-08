package com.taskmaster.aayushf.taskmaster

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by aayushf on 13/6/17.
 */
class TasksTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm), AnkoLogger {
    var tagtodisplay: String? = null

    fun setTagToDisplay(tagtodisplay: String?) {
        this.tagtodisplay = tagtodisplay
        this@TasksTabAdapter.notifyDataSetChanged()

    }
    override fun getItemPosition(`object`: Any?): Int {
        Log.d("Adapter", "getItemAtPosition$tagtodisplay")
        val rf = `object` as RecyclerFragment?
        rf!!.refreshFragment(tagtodisplay)
        return super.getItemPosition(`object`)
    }


    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return RecyclerFragment.newInstance(null, false)

        } else {
            return RecyclerFragment.newInstance(null, true)
        }

    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "PENDING"
            1 -> return "DONE"

        }
        return null
    }
}
