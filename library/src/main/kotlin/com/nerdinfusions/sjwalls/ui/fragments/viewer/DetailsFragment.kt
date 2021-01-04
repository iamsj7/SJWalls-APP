package com.nerdinfusions.sjwalls.ui.fragments.viewer

import android.view.View
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nerdinfusions.sjwalls.R
import com.nerdinfusions.sjwalls.data.models.Wallpaper
import com.nerdinfusions.sjwalls.extensions.resources.dpToPx
import com.nerdinfusions.sjwalls.extensions.utils.MAX_FRAMES_PALETTE_COLORS
import com.nerdinfusions.sjwalls.extensions.utils.bestSwatches
import com.nerdinfusions.sjwalls.extensions.views.findView
import com.nerdinfusions.sjwalls.extensions.views.setPaddingLeft
import com.nerdinfusions.sjwalls.extensions.views.setPaddingRight
import com.nerdinfusions.sjwalls.ui.adapters.WallpaperDetailsAdapter
import com.nerdinfusions.sjwalls.ui.decorations.DetailsGridSpacingItemDecoration
import com.nerdinfusions.sjwalls.ui.fragments.base.BaseBottomSheet
import kotlin.math.roundToInt

class DetailsFragment : BaseBottomSheet() {

    private var shouldShowPaletteDetails: Boolean = true

    var wallpaper: Wallpaper? = null
        set(value) {
            field = value
            wallpaperDetailsAdapter.wallpaper = value
            wallpaperDetailsAdapter.notifyDataSetChanged()
        }

    var palette: Palette? = null
        set(value) {
            field = value
            wallpaperDetailsAdapter.paletteSwatches = ArrayList(value?.bestSwatches.orEmpty())
        }

    private val wallpaperDetailsAdapter: WallpaperDetailsAdapter by lazy {
        WallpaperDetailsAdapter(wallpaper, shouldShowPaletteDetails)
    }

    override fun getContentView(): View? {
        val view = View.inflate(context, R.layout.fragment_recyclerview, null)

        val recyclerView: RecyclerView? by view.findView(R.id.recycler_view)
        recyclerView?.setPaddingLeft(8.dpToPx)
        recyclerView?.setPaddingRight(8.dpToPx)
        val columns = (MAX_FRAMES_PALETTE_COLORS / 2.0).roundToInt()
        val decoration = DetailsGridSpacingItemDecoration(8.dpToPx)
        val lm = GridLayoutManager(context, columns)

        recyclerView?.layoutManager = lm
        wallpaperDetailsAdapter.setLayoutManager(lm)
        recyclerView?.adapter = wallpaperDetailsAdapter
        recyclerView?.addItemDecoration(decoration)

        return view
    }

    companion object {
        @JvmStatic
        fun create(
            wallpaper: Wallpaper? = null,
            palette: Palette? = null,
            shouldShowPaletteDetails: Boolean = true
        ) =
            DetailsFragment().apply {
                this.wallpaper = wallpaper
                this.palette = palette
                this.shouldShowPaletteDetails = shouldShowPaletteDetails
            }
    }
}