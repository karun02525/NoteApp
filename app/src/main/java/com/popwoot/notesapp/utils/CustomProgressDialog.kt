package com.popwoot.notesapp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.popwoot.notesapp.R



class CustomProgressDialog(context: Context, isCancelable: Boolean) : Dialog(context) {
    init {
        window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_progress_bar)
        setCancelable(isCancelable)
    }

}
