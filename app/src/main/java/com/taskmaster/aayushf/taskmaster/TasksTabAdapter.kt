package com.taskmaster.aayushf.taskmaster

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log

/**
 * Created by aayushf on 13/6/17.
 */
class TasksTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var tagtodisplay: String? = null
        set(value) {
            this.notifyDataSetChanged()

        }
    override fun getItemPosition(`object`: Any?): Int {
        Log.d("Adapter", "getItemAtPosition")
        return PagerAdapter.POSITION_NONE
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
