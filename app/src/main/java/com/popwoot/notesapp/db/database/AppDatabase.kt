package com.popwoot.notesapp.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.popwoot.notesapp.db.dao.NoteDao
import com.popwoot.notesapp.db.entity.NoteModel
import com.popwoot.notesapp.ui.base.Constant.DatabaseName
import com.popwoot.notesapp.utils.Converters

@Database(entities = [NoteModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) { INSTANCE ?: buildDatabase(context).also { INSTANCE = it } }

        private fun buildDatabase(context: Context) =
            databaseBuilder(context.applicationContext, AppDatabase::class.java, DatabaseName).build()
    }
}