package com.taskmaster.aayushf.taskmaster

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import co.zsmb.materialdrawerkt.builders.accountHeader
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.profile.profile
import com.mikepenz.materialdrawer.holder.BadgeStyle
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import java.util.*

class MainActivity : AppCompatActivity(), AnkoLogger {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: TasksTabAdapter? = null
    private var c: Context? = null
    var i: Int = 0
    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null
    var toolbara: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        info(intent.getIntExtra("taskprimk", -1))
        if (intent.getIntExtra("taskprimk", -1) != -1) {

            val taskpk = intent.getIntExtra("taskprimk", 0)
            info(taskpk)
            Realm.init(this)
            val r = Realm.getDefaultInstance()
            r.beginTransaction()
            val t = r.where(Task::class.java).equalTo("primk", taskpk).findFirst()
            t.done = true
            t.datedone = Calendar.getInstance().timeInMillis
        }




        toolbara = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbara)
        toolbartv.typeface = Typeface.createFromAsset(this.assets, "font/Bitter-Bold.ttf")
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = TasksTabAdapter(supportFragmentManager)
        Realm.init(this)
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container) as ViewPager?
        mViewPager!!.adapter = mSectionsPagerAdapter
        title = ""


        val tabLayout = findViewById(R.id.tabs) as TabLayout?
        tabLayout?.setupWithViewPager(mViewPager)
        c = this
        createDrawer()



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            startActivity<AdderActivity>()
        }


    }


    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
*/
    fun createDrawer() {
        var dwr = drawer {
            toolbar = toolbara!!

            accountHeader {
                background = R.drawable.header
                profile("Aayush Fadia", "aayushf@gmail.com") {}
            }
        }
        Realm.init(this)
        dwr.addItem(PrimaryDrawerItem().withName("Stats").withOnDrawerItemClickListener { _, _, _ ->
            startActivity<StatsActivity>()
            true
        })
        var allTags = Task.getAllTagsTaskNo()
        dwr.addItem(PrimaryDrawerItem().withName("All Tags").withSelectable(true).withBadge(Realm.getDefaultInstance().where(Task::class.java).count().toString()).withBadgeStyle(BadgeStyle(Task.colours[4], Task.colours[4])).withOnDrawerItemClickListener { view, position, drawerItem ->
            mSectionsPagerAdapter?.setTagToDisplay(null)
            true
        })

        allTags.forEach { tag: Pair<String, Int> ->
            dwr.addItem(PrimaryDrawerItem().withName(tag.first).withBadge(tag.second.toString()).withBadgeStyle(BadgeStyle(Task.colours[4], Task.colours[4])).withSelectable(true).withOnDrawerItemClickListener { _, _, _ ->
                info(tag)
                mSectionsPagerAdapter?.setTagToDisplay(tag.first)
                true
            })

        }


    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
}








