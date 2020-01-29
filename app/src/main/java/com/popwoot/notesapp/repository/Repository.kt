package com.popwoot.notesapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.popwoot.notesapp.db.dao.NoteDao
import com.popwoot.notesapp.db.database.AppDatabase
import com.popwoot.notesapp.db.entity.NoteModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class Repository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    var mDao: NoteDao = AppDatabase.getInstance(application).noteDao()

    fun getAllNote() = mDao.getAllNotes()

    fun findCreatedDates(createAt: Long): LiveData<List<NoteModel>> {
       return mDao.findCreatedDates(createAt)
    }

    fun getSearchList(title: String): LiveData<List<NoteModel>> {
       return mDao.getSearchList(title)
    }


    fun insertNote(note: NoteModel) {
        launch { insertNoteSuspend(note) }
    }


    private suspend fun insertNoteSuspend(note: NoteModel) {
        withContext(Dispatchers.IO) {
            mDao.insert(note)
        }
    }


}