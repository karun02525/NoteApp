package com.popwoot.notesapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.popwoot.notesapp.db.entity.NoteModel
import java.util.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteModel")
    fun getAllNotes(): LiveData<List<NoteModel>>

    @Insert
    fun insert(vararg note: NoteModel)


    @Query("SELECT * FROM NoteModel WHERE createAt <:createAt")
    fun findCreatedDates(createAt: Long): LiveData<List<NoteModel>>

    @Query("SELECT * FROM NoteModel WHERE title LIKE :title")
    fun getSearchList(title: String): LiveData<List<NoteModel>>

}