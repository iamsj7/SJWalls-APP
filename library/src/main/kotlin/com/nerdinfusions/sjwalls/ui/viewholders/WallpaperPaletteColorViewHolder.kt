package com.nerdinfusions.sjwalls.ui.viewholders

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.palette.graphics.Palette
import com.afollestad.sectionedrecyclerview.SectionedViewHolder
import com.nerdinfusions.sjwalls.R
import com.nerdinfusions.sjwalls.extensions.context.toast
import com.nerdinfusions.sjwalls.extensions.resources.toHexString
import com.nerdinfusions.sjwalls.extensions.utils.bestTextColor
import com.nerdinfusions.sjwalls.extensions.views.context
import com.nerdinfusions.sjwalls.extensions.views.findView

class WallpaperPaletteColorViewHolder(view: View) : SectionedViewHolder(view) {

    private val colorBtn: AppCompatButton? by view.findView(R.id.palette_color_btn)

    fun bind(swatch: Palette.Swatch? = null) {
        swatch ?: return
        colorBtn?.setBackgroundColor(swatch.rgb)
        colorBtn?.setTextColor(swatch.bestTextColor)
        colorBtn?.text = swatch.rgb.toHexString()
        colorBtn?.setOnClickListener {
            val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager
            clipboard?.setPrimaryClip(
                ClipData.newPlainText("label", swatch.rgb.toHexString())
            )
            context.toast(R.string.copied_to_clipboard)
        }
    }
}