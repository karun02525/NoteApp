package com.popwoot.notesapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.popwoot.notesapp.R
import com.popwoot.notesapp.db.entity.NoteModel
import com.popwoot.notesapp.mvvm.NoteViewModel
import com.popwoot.notesapp.ui.base.BaseActivity
import com.popwoot.notesapp.utils.CustomProgressDialog
import com.popwoot.notesapp.utils.hideSoftKeyboard
import com.winds.imagepickerlibrary.ImagePicker
import com.winds.imagepickerlibrary.OnImagePickedListener
import kotlinx.android.synthetic.main.activity_add_note.*


class AddNoteActivity : BaseActivity() {

    private var filePath: Uri? = null
    private val pd by lazy { CustomProgressDialog(this, false) }
    private val mViewModel by lazy { ViewModelProviders.of(this).get(NoteViewModel::class.java) }
    private lateinit var imagePicker: ImagePicker
    private val TAG = this::class.java.simpleName
    private var noteTitle = ""
    private var noteDesc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

    }

    fun btnCreate(view: View) {
       if(validation()){
           submit()
       }
    }


    private fun submit(){
        val path=filePath?:""
        mViewModel.insertData(NoteModel(0,noteTitle,noteDesc,filePath.toString(),System.currentTimeMillis()))
        finish()
        Log.d(TAG, "submit: $noteTitle $noteDesc $path")
}


    fun btnUploadImage(view: View) {

        imagePicker = ImagePicker(this, null, object : OnImagePickedListener {
            override fun onImagePicked(imageUri: Uri?) {
                imageUpload.setImageURI(imageUri)
                filePath = imageUri
                hideShowView(true)
            }
        }).setWithImageCrop()
        imagePicker.choosePicture(true)
        hideShowView(false)
    }

    fun btnBack(v: View) {
        onBackPressed()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker.handleActivityResult(resultCode, requestCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        imagePicker.handlePermission(requestCode, grantResults)
    }


    private fun hideShowView(flag: Boolean) {
        if (flag) {
            imageUpload.visibility = View.VISIBLE
            rl_layout_upload.visibility = View.GONE
        } else {
            imageUpload.visibility = View.GONE
            rl_layout_upload.visibility = View.VISIBLE

        }
    }

    private fun validation(): Boolean {
        noteTitle = editTitle!!.text.toString()
        noteDesc = edit_desc!!.text.toString()
        return when {
            noteTitle.isBlank() -> {
                hideSoftKeyboard()
                showSnackBarMessage(getString(R.string.enter_title))
                false
            }
            noteDesc.isBlank() -> {
                hideSoftKeyboard()
                showSnackBarMessage(getString(R.string.enter_descr))
                false
            }
            else -> {
                true
            }
        }
    }
}
