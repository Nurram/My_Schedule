package com.github.nurram.myschedule.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.nurram.myschedule.ActivitiesRepository
import com.github.nurram.myschedule.model.Activities

class HomeViewModel(private val repository: ActivitiesRepository) : ViewModel() {
    var activities: LiveData<List<Activities>>? = null

    fun getActivities(date: String): LiveData<List<Activities>>? =
        activities ?: repository.getActivities(date)

    fun deleteActivities(activities: Activities) {
        repository.deleteActivities(activities)
    }
}