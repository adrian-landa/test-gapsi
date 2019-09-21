package com.gapsi.productsearcher.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gapsi.productsearcher.constants.App
import com.gapsi.productsearcher.data.Converters
import com.gapsi.productsearcher.data.dao.WordDAO
import com.gapsi.productsearcher.data.entites.Word

@Database(
    entities = [Word::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun wordDAO(): WordDAO

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDB {
            return Room.databaseBuilder(context, AppDB::class.java, App.DB_NAME)
                .build()
        }

    }
}