package com.github.nurram.myschedule.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.nurram.myschedule.model.Activities

@Database(entities = [Activities::class], version = 1)
abstract class ActivitiesDatabase : RoomDatabase() {
    abstract fun activitiesDao(): ActivitiesDao

    companion object {
        private var instance: ActivitiesDatabase? = null
        fun getInstance(context: Context): ActivitiesDatabase? {
            if (instance == null) {
                synchronized(ActivitiesDatabase::class.java) {
                    instance = Room.databaseBuilder(context, ActivitiesDatabase::class.java, "myDB")
                        .build()
                }
            }

            return instance
        }

    }
}