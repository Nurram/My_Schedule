package com.github.nurram.myschedule.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.nurram.myschedule.model.Activities

@Dao
interface ActivitiesDao {

    @Query("SELECT * FROM activities_table WHERE date = :dateString ORDER BY time ASC")
    fun getActivities(dateString: String): LiveData<List<Activities>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveActivities(activities: Activities)

    @Delete
    fun deleteActivities(activities: Activities)
}