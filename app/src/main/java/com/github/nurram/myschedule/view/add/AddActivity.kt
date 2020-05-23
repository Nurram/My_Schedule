package com.github.nurram.myschedule.view.add

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.nurram.myschedule.ActivitiesRepository
import com.github.nurram.myschedule.DateUtil
import com.github.nurram.myschedule.R
import com.github.nurram.myschedule.db.ActivitiesDatabase
import com.github.nurram.myschedule.model.Activities
import com.github.nurram.myschedule.view.ViewModelFactory
import com.github.nurram.myschedule.view.home.HomeFragment
import kotlinx.android.synthetic.main.add_activity_content.*
import kotlinx.android.synthetic.main.appbar.*


class AddActivity : AppCompatActivity() {
    private lateinit var viewModel: AddActivityViewModel
    private lateinit var curDate: String

    private var isSaved = false
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = ActivitiesDatabase.getInstance(this)
        val repository = ActivitiesRepository(db!!.activitiesDao())
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(AddActivityViewModel::class.java)

        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                add_spinner_category.adapter = it
            }

        isSaved = intent.getBooleanExtra(HomeFragment.SAVED, false)
        if (isSaved) {
            initSavedUI()

        } else {
            curDate = intent.getStringExtra(HomeFragment.DATE)!!
            add_date.text = curDate
        }


        add_timepicker.setIs24HourView(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val activity = getActivities()

        if (activity != null) {
            viewModel.saveActivities(activity)
        }

        finish()

        return true
    }

    private fun initSavedUI() {
        val activities = intent.getParcelableExtra<Activities>(HomeFragment.ACTIVITIES)!!
        val date = DateUtil.dateStringFromMillis(activities.time)
        val time = DateUtil.timeStringFromMillis(activities.time)
        val times = time.split(":")
        Log.d("TAG", "Date $date, Time $time")

        id = activities.id
        curDate = date
        add_date.text = curDate
        add_timepicker.hour = times[0].toInt()
        add_timepicker.minute = times[1].toInt()
        add_title.setText(activities.name)
        add_spinner_category.setSelection(activities.category)
        add_description.setText(activities.desc)
    }

    private fun getActivities(): Activities? {
        val formattedTime =
            "$curDate ${DateUtil.formatTime(add_timepicker.hour, add_timepicker.minute)}"
        Log.d("TAG", "Formatted Time $formattedTime")

        return if (isFormFilled()) {
            DateUtil.toTimeMillis(formattedTime)?.let {
                Activities(
                    id = id,
                    name = add_title.text.toString(),
                    date = curDate,
                    time = it,
                    category = add_spinner_category.selectedItemPosition,
                    desc = add_description.text.toString()
                )
            }
        } else {
            Toast.makeText(this, "Please fill the title", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun isFormFilled(): Boolean {
        if (add_title.text.toString().isEmpty()) {
            return false
        }

        return true
    }
}
