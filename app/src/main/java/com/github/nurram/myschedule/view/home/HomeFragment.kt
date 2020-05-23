package com.github.nurram.myschedule.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nurram.myschedule.ActivitiesRepository
import com.github.nurram.myschedule.DateUtil
import com.github.nurram.myschedule.R
import com.github.nurram.myschedule.db.ActivitiesDatabase
import com.github.nurram.myschedule.model.Activities
import com.github.nurram.myschedule.view.ViewModelFactory
import com.github.nurram.myschedule.view.add.AddActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private lateinit var db: ActivitiesDatabase
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    companion object {
        const val ACTIVITIES = "activities"
        const val DATE = "date"
        const val SAVED = "saved"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = ActivitiesDatabase.getInstance(context)!!
        adapter = HomeAdapter(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = ActivitiesRepository(db.activitiesDao())
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)


        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(bottom_sheet_parent)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        home_recycler.layoutManager = LinearLayoutManager(context)
        home_recycler.setHasFixedSize(true)
        home_recycler.adapter = adapter
        adapter.setDeleteClick(object : HomeAdapter.OnClickActivities {
            override fun onDeleteClick(activities: Activities) {
                viewModel.deleteActivities(activities)
            }

            override fun onItemClick(activities: Activities) {
                val intent = Intent(context, AddActivity::class.java)
                intent.putExtra(ACTIVITIES, activities)
                intent.putExtra(SAVED, true)
                startActivity(intent)
            }
        })

        var day = 0
        var month = 0
        var year = 0

        home_calendar.setOnDateChangeListener { _, yr, mnth, dayOfMonth ->
            day = dayOfMonth
            month = mnth + 1
            year = yr

            getData(DateUtil.formatDate(day, month, year))
        }

        getData(DateUtil.getCurrentDate())

        home_fab.setOnClickListener {
            val intent = Intent(context, AddActivity::class.java)

            val date = if (day == 0) {
                DateUtil.getCurrentDate()
            } else {
                DateUtil.formatDate(day, month, year)
            }
            intent.putExtra(DATE, date)
            startActivity(intent)
        }
    }

    private fun getData(date: String) {
        viewModel.getActivities(date)
            ?.observe(viewLifecycleOwner, Observer {
                adapter.setData(it as ArrayList<Activities>)
            })
    }
}
