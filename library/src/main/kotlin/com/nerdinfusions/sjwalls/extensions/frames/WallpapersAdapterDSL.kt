package com.nerdinfusions.sjwalls.extensions.frames

import com.nerdinfusions.sjwalls.data.models.Wallpaper
import com.nerdinfusions.sjwalls.ui.adapters.WallpapersAdapter
import com.nerdinfusions.sjwalls.ui.viewholders.WallpaperViewHolder

internal fun wallpapersAdapter(
    canShowFavoritesButton: Boolean = true,
    canModifyFavorites: Boolean = true,
    block: WallpapersAdapter.() -> Unit
): WallpapersAdapter =
    WallpapersAdapter(canShowFavoritesButton, canModifyFavorites).apply(block)

internal fun WallpapersAdapter.onClick(what: (Wallpaper, WallpaperViewHolder) -> Unit) {
    this.onClick = what
}

internal fun WallpapersAdapter.onFavClick(what: (Boolean, Wallpaper) -> Unit) {
    this.onFavClick = what
}