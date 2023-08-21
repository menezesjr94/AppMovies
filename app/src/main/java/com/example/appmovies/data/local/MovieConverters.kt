package com.example.appmovies.data.local

import androidx.room.TypeConverter

class MovieConverters {

    @TypeConverter
    fun fromList(genreIds: List<Int>): String {
        return genreIds.joinToString(",")
    }

    @TypeConverter
    fun toList(genreIdsString: String): ArrayList<Int> {
        return genreIdsString.split(",").map { it.toInt() }.toCollection(ArrayList())
    }

}