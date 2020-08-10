package com.nerdinfusions.sjwalls.data.network

import com.nerdinfusions.sjwalls.data.models.Wallpaper
import retrofit2.http.GET
import retrofit2.http.Url

interface WallpapersJSONService {
    @GET
    suspend fun getJSON(@Url url: String): List<Wallpaper>
}