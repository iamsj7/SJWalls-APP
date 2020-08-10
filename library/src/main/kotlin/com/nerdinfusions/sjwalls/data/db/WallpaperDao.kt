package com.nerdinfusions.sjwalls.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nerdinfusions.sjwalls.data.models.Wallpaper

@Dao // Data Access Object
interface WallpaperDao {
    @Query("select * from wallpapers")
    fun getAllWallpapers(): List<Wallpaper>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // insert into wallpapers values (name, author, url, ...)
    fun insert(wallpaper: Wallpaper)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(wallpapers: List<Wallpaper>)

    @Delete
    fun delete(wallpaper: Wallpaper)

    @Query("delete from wallpapers where url = :url")
    fun deleteByUrl(url: String)

    @Query("delete from wallpapers")
    fun nuke()
}