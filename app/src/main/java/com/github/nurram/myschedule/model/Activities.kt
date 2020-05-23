package com.github.nurram.myschedule.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "activities_table")
data class Activities(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val date: String,
    val time: Long,
    val category: Int,
    val desc: String = ""
) : Parcelable