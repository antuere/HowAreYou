package com.example.zeroapp.dataBase

import android.text.format.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "day_table")
data class Day(

    @PrimaryKey(autoGenerate = true)
    var dayId: Long = 0L,

    @ColumnInfo(name = "date")
    val currentDate: String = DateFormat.format("dd/MM/yy", System.currentTimeMillis()).toString(),

    @ColumnInfo(name = "image_id")
    val imageId: Int,

    @ColumnInfo(name = "day_text")
    var dayText: String,

)