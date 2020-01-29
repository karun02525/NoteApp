package com.popwoot.notesapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.popwoot.notesapp.R
import com.popwoot.notesapp.db.entity.NoteModel
import com.popwoot.notesapp.utils.agoMinute
import com.popwoot.notesapp.utils.loadImage
import kotlinx.android.synthetic.main.adapter_note_list.view.*
import java.io.File


class NoteAdapter(var list: List<NoteModel>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_note_list, parent, false)
            return ViewHolder(v)
        }
        override fun getItemCount(): Int {
            return list.size
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(list[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItems(model: NoteModel) {
                itemView.apply {

                    if(model.fileUri=="null"){
                        thumbnail.visibility=View.GONE
                    }else{
                        thumbnail.loadImage(File(model.fileUri!!).toString())
                        thumbnail.visibility=View.VISIBLE
                    }

                   noteTitle.text = model.title
                   noteInfo.text = model.description
                    noteTime.text = model.createAt.agoMinute()

                    Log.d("Adapter", "bindItems: "+ model.fileUri)
                }
            }
        }

}
