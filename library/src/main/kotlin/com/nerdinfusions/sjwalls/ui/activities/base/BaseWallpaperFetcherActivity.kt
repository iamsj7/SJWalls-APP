package com.nerdinfusions.sjwalls.ui.activities.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.nerdinfusions.sjwalls.R
import com.nerdinfusions.sjwalls.data.Preferences
import com.nerdinfusions.sjwalls.data.models.Wallpaper
import com.nerdinfusions.sjwalls.data.workers.WallpaperDownloader
import com.nerdinfusions.sjwalls.data.workers.WallpaperDownloader.Companion.DOWNLOAD_FILE_EXISTED
import com.nerdinfusions.sjwalls.data.workers.WallpaperDownloader.Companion.DOWNLOAD_PATH_KEY
import com.nerdinfusions.sjwalls.extensions.context.toast
import com.nerdinfusions.sjwalls.extensions.resources.getMimeType
import com.nerdinfusions.sjwalls.extensions.resources.getUri
import com.nerdinfusions.sjwalls.extensions.views.snackbar
import java.io.File

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseWallpaperFetcherActivity<out P : Preferences> :
    BaseFavoritesConnectedActivity<P>() {

    internal val workManager: WorkManager by lazy { WorkManager.getInstance(this) }
    internal var wallpaperDownloadUrl: String = ""

    internal fun initDownload(wallpaper: Wallpaper?) {
        wallpaperDownloadUrl = wallpaper?.url.orEmpty()
    }

    internal fun startDownload() {
        cancelWorkManagerTasks()
        val newDownloadTask = WallpaperDownloader.buildRequest(wallpaperDownloadUrl)
        newDownloadTask?.let { task ->
            workManager.enqueue(newDownloadTask)
            workManager.getWorkInfoByIdLiveData(task.id)
                .observe(this, Observer { info ->
                    if (info != null && info.state.isFinished) {
                        if (info.state == WorkInfo.State.SUCCEEDED) {
                            val path = info.outputData.getString(DOWNLOAD_PATH_KEY) ?: ""
                            val existed = info.outputData.getBoolean(DOWNLOAD_FILE_EXISTED, false)
                            if (existed) onDownloadExistent(path)
                            else onDownloadQueued()
                        } else if (info.state == WorkInfo.State.FAILED) {
                            onDownloadError()
                        }
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelWorkManagerTasks()
    }

    fun cancelWorkManagerTasks() {
        workManager.cancelAllWork()
        workManager.pruneWork()
    }

    private fun onDownloadQueued() {
        try {
            currentSnackbar = snackbar(R.string.download_starting, anchorViewId = snackbarAnchorId)
        } catch (e: Exception) {
        }
        cancelWorkManagerTasks()
    }

    private fun onDownloadExistent(path: String) {
        try {
            val file = File(path)
            val fileUri: Uri? = file.getUri(this) ?: Uri.fromFile(file)
            currentSnackbar =
                snackbar(R.string.downloaded_previously, Snackbar.LENGTH_LONG, snackbarAnchorId) {
                    fileUri?.let {
                        setAction(R.string.open) {
                            try {
                                startActivity(Intent().apply {
                                    action = Intent.ACTION_VIEW
                                    setDataAndType(fileUri, file.getMimeType("image/*"))
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                })
                            } catch (e: Exception) {
                                toast(R.string.error)
                            }
                        }
                    }
                }
        } catch (e: Exception) {
        }
        cancelWorkManagerTasks()
    }

    internal fun onDownloadError() {
        try {
            currentSnackbar =
                snackbar(R.string.unexpected_error_occurred, anchorViewId = snackbarAnchorId)
        } catch (e: Exception) {
        }
        cancelWorkManagerTasks()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(WALLPAPER_URL_KEY, wallpaperDownloadUrl)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        wallpaperDownloadUrl = savedInstanceState.getString(WALLPAPER_URL_KEY, "") ?: ""
    }

    companion object {
        private const val WALLPAPER_URL_KEY = "wallpaper_download_url"
    }
}
