package com.daneng.dict.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Word::class, WordFts::class, Sense::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        fun build(context: Context): DictionaryDatabase =
            Room.databaseBuilder(context, DictionaryDatabase::class.java, "dictionary.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
