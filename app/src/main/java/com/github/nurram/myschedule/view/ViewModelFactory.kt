package com.github.nurram.myschedule.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.nurram.myschedule.ActivitiesRepository
import com.github.nurram.myschedule.view.add.AddActivityViewModel
import com.github.nurram.myschedule.view.home.HomeViewModel

class ViewModelFactory(private val repository: ActivitiesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(repository) as T
            else ->
                AddActivityViewModel(repository) as T
        }
    }
}