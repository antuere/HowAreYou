package com.example.zeroapp.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.zeroapp.util.getString
import java.util.*


@Entity(tableName = "day_table")
data class Day(

    @PrimaryKey(autoGenerate = true)
    var dayId: Long = 0L,

    @ColumnInfo(name = "date")
    val currentDate: Calendar = Calendar.getInstance(),

    @ColumnInfo(name = "image_id")
    val imageId: Int,

    @ColumnInfo(name = "day_text")
    var dayText: String,

    @ColumnInfo(name = "date_text")
    val currentDateString: String = currentDate.getString()

)