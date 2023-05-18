package com.tugrulkara.movieapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    @TypeConverter
    fun fromList(list: List<Int>): String {
        val type = object: TypeToken<List<Int>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun fromString(value: String): List<Int> {
        val type = object: TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, type)
    }

}