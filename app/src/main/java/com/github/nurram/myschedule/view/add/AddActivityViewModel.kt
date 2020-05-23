package com.github.nurram.myschedule.view.add

import androidx.lifecycle.ViewModel
import com.github.nurram.myschedule.ActivitiesRepository
import com.github.nurram.myschedule.model.Activities

class AddActivityViewModel(private val repository: ActivitiesRepository) : ViewModel() {
    fun saveActivities(activities: Activities) = repository.saveActivities(activities)
}