package com.popwoot.notesapp.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.popwoot.notesapp.db.entity.NoteModel
import com.popwoot.notesapp.repository.Repository

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    var mRepository: Repository? = null

    init {
        mRepository = Repository(application)
    }

    fun getAllNotes():LiveData<List<NoteModel>> {
       return mRepository?.getAllNote()!!
    }

    suspend fun findCreatedDates(createAt: Long):LiveData<List<NoteModel>> {
       return mRepository?.findCreatedDatesMain(createAt)!!
    }

    fun getSearchList(title: String):LiveData<List<NoteModel>> {
       return mRepository?.getSearchList(title)!!
    }

    fun insertData(note: NoteModel) {
        mRepository?.insertNote(note)
    }

}