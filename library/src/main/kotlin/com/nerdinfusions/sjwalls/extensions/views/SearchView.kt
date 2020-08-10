package com.nerdinfusions.sjwalls.extensions.views

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import com.nerdinfusions.sjwalls.R
import com.nerdinfusions.sjwalls.extensions.context.color
import com.nerdinfusions.sjwalls.extensions.context.resolveColor
import com.nerdinfusions.sjwalls.extensions.resources.tint
import com.nerdinfusions.sjwalls.extensions.resources.withAlpha

fun SearchView.tint(
    @ColorInt color: Int =
        context.resolveColor(R.attr.colorOnPrimary, context.color(R.color.onPrimary)),
    @ColorInt hintColor: Int = color
) {
    val field: EditText? by findView(R.id.search_src_text)
    field?.setTextColor(color)
    field?.setHintTextColor(if (hintColor == color) hintColor.withAlpha(0.6F) else hintColor)
    field?.tint(color)

    val plate: View? by findView(R.id.search_plate)
    plate?.background = null

    val iconsIds = arrayOf(
        R.id.search_button,
        R.id.search_close_btn,
        R.id.search_go_btn,
        R.id.search_voice_btn,
        R.id.search_mag_icon
    )
    iconsIds.forEach {
        try {
            findViewById<ImageView?>(it)?.tint(color)
        } catch (e: Exception) {
        }
    }
}