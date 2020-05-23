package com.github.nurram.myschedule

import androidx.lifecycle.LiveData
import com.github.nurram.myschedule.db.ActivitiesDao
import com.github.nurram.myschedule.model.Activities
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActivitiesRepository(private val dao: ActivitiesDao) {

    fun getActivities(date: String): LiveData<List<Activities>> = dao.getActivities(date)
    fun saveActivities(activities: Activities) {
        GlobalScope.launch {
            dao.saveActivities(activities)
        }
    }

    fun deleteActivities(activities: Activities) {
        GlobalScope.launch {
            dao.deleteActivities(activities)
        }
    }
}