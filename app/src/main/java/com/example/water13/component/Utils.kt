package com.example.water13.component

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

fun Activity.toast(content: String?) {
    content?.let {
        if (it.isNotEmpty()) {
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Fragment.toast(content: String?) {
    content?.let {
        if (it.isNotEmpty()) {
            Toast.makeText(activity, content, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Activity.toastUiThread(content: String?) {
    runOnUiThread { toast(content) }
}

fun Fragment.toastUiThread(content: String?) {
    activity?.toastUiThread(content)
}

@BindingAdapter("formatted_time")
fun TextView.showFormattedTime(timeStamp: Int) {
    this.text = SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.getDefault()).format(timeStamp * 1000L)
}

fun loadPoker(context: Context, imageList: List<String>, viewList: List<ImageView>) {
    val resources = context.resources
    for (i in 0..12) {
        Timber.d("heihei ${imageList[i]}")
        val resourceId = resources?.getIdentifier(
            imageList[i], "drawable",
            context.packageName
        )
        val image = resources?.getDrawable(resourceId!!, null)
        Glide.with(context).load(image).override(Target.SIZE_ORIGINAL).into(viewList[i])
    }
}

fun pokerStringToFileName(cards: String): List<String> {
    return cards.split(" ").map { card ->
        val color = when (card[0]) {
            '&' -> "hongxin"
            '$' -> "heitao"
            '#' -> "fangkuai"
            '*' -> "meihua"
            else -> ""
        }
        val num = when (val originNum = card.substring(1)) {
            "A" -> "a"
            "J" -> "j"
            "Q" -> "q"
            "K" -> "k"
            else -> originNum
        }
        "c_$color$num"
    }
}