package com.popwoot.notesapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.TypeConverter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.popwoot.carouselbanner.interfaces.CarouselImageFactory
import com.popwoot.notesapp.R
import com.winds.timesago.TimeAgo
import com.winds.timesago.TimeAgoMessages
import java.util.*


/*Animation Right To Left */
fun AppCompatActivity.leftToRight() {
    this.overridePendingTransition(R.anim.enter, R.anim.exit)
}

fun AppCompatActivity.rightToLeft() {
    this.overridePendingTransition(R.anim.left_to_right_silder, R.anim.right_to_left__silder)
}

//Custom Snackbar
fun AppCompatActivity.showSnackBar(message: String, color: Int): Snackbar {
    val sb = Snackbar.make(this.findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_SHORT)
    sb.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    val textView = sb.view.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(color)
    sb.show()
    return sb
}

fun Context.hideSoftKeyboard() {
    try {
        val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow((this as Activity).currentFocus!!.windowToken, 0)
    } catch (e: Exception) {
    }
}

fun Long.agoMinute():String{
    val localeBylanguageTag = Locale.forLanguageTag("en")
    val messages = TimeAgoMessages.Builder().withLocale(localeBylanguageTag).build()
    return TimeAgo.using(this, messages)
}

fun ImageView.loadImage(url: String) {
    val circularProgressDrawable =
        CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(url)
        .placeholder(circularProgressDrawable)
        .fitCenter()
        .into(this)
}

class ImageFactory : CarouselImageFactory {
    override fun onLoadFactory(url: String, view: ImageView) {
        Glide.with(view).load(url).into(view)
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

