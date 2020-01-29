package com.popwoot.notesapp.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.popwoot.notesapp.R
import kotlinx.android.synthetic.main.activity_filter.*


class FilterActivity : AppCompatActivity() {

    private var dateFilter=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)


        dateFilter=intent.getStringExtra("dateFilter")?:""


        if (dateFilter != ""){
            date.isChecked=true
            btnClearAll.setTextColor(Color.BLUE)
        }

        date.setOnCheckedChangeListener { _, isChecked ->
            dateFilter= System.currentTimeMillis().toString()
            btnClearAll.setTextColor(Color.BLUE)
        }
        media.setOnCheckedChangeListener { _, isChecked ->
            btnClearAll.setTextColor(Color.BLUE)
        }


        btnClearAll.setOnClickListener {
            btnClearAll()
        }
        btnApply.setOnClickListener {
            btnApply()
        }

    }



    fun btnApply() {
        val returnIntent = Intent()
        returnIntent.putExtra("dateFilter", dateFilter)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()

    }

    private fun btnClearAll() {
        date.isChecked=false
        media.isChecked=false
        dateFilter=""
        btnClearAll.setTextColor(Color.WHITE)
    }

}
