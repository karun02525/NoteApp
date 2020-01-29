package com.popwoot.notesapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int= 0,
    val title: String?,
    val description:String?,
    val fileUri:String?=null,
    val createAt: Long
)
