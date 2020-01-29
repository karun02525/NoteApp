package com.popwoot.notesapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
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
        }

        date.setOnCheckedChangeListener { _, isChecked ->
            dateFilter= System.currentTimeMillis().toString()
        }
        media.setOnCheckedChangeListener { _, isChecked ->

        }

    }



    fun btnApply(view: View) {
        val returnIntent = Intent()
        returnIntent.putExtra("dateFilter", dateFilter)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()

    }

    fun btnClearAll(view: View) {
        date.isChecked=false
        media.isChecked=false
        dateFilter=""
    }
}
