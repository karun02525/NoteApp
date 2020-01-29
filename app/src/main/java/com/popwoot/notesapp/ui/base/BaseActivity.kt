package com.popwoot.notesapp.ui.base



import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.popwoot.notesapp.utils.leftToRight
import com.popwoot.notesapp.utils.rightToLeft
import com.popwoot.notesapp.utils.showSnackBar



abstract class BaseActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leftToRight()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        rightToLeft()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        rightToLeft()
    }

    fun showSnackBarMessage(str:String) {
        showSnackBar(str, Color.WHITE)
    }

}
