package com.taskmaster.aayushf.taskmaster


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import io.realm.Realm
import io.realm.RealmResults
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.startActivity


/**
 * A simple [Fragment] subclass.
 * Use the [RecyclerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecyclerFragment : Fragment(), AnkoLogger {

    // TODO: Rename and change types of parameters



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val v: View = inflater!!.inflate(R.layout.fragment_recycler, container, false)
        val rv: RecyclerView = v.find(R.id.rvfrag)
        rv?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        info("OnCreateView")
        var fastAdapter: FastItemAdapter<TaskViewItem> = FastItemAdapter()
        fastAdapter.withOnLongClickListener({ v, _, _, _ ->
            startActivity<AdderActivity>("task" to v.find<TextView>(R.id.tvtaskcard).text.toString())
            Log.d("RecyclerFragment", "LongClicked")
            true
        })
        Realm.init(activity)
        val tagtodisplay: String? = arguments.getString("tagtodisplay")
        var listoftasks: RealmResults<Task>
        if (tagtodisplay != null) {
            listoftasks = Realm.getDefaultInstance().where(Task::class.java).equalTo("done", arguments.getBoolean("done")).contains("tag", tagtodisplay).findAll()


        } else {
            listoftasks = Realm.getDefaultInstance().where(Task::class.java).equalTo("done", arguments.getBoolean("done")).findAll()
        }
        warn(listoftasks.size)
        fastAdapter.add(TaskViewItem.getListOfTaskView(listoftasks))
        
        rv.adapter = fastAdapter

        rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        debug("OnCreate Called")


        return v
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val DONE = "param1"
        private val TAG = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment RecyclerFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(tagtodiaplsy: String?, done: Boolean): RecyclerFragment {
            val fragment = RecyclerFragment()
            val args = Bundle()
            args.putString("tagtodisplay", tagtodiaplsy)
            args.putBoolean("done", done)
            fragment.arguments = args
            Log.d("RecyclerFragment", "NewInstace Called")
            return fragment
        }

    }

}// Required empty public constructor
