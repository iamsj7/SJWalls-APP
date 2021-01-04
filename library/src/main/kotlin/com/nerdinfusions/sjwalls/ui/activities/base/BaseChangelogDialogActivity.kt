package com.nerdinfusions.sjwalls.ui.activities.base

import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.nerdinfusions.sjwalls.R
import com.nerdinfusions.sjwalls.data.Preferences
import com.nerdinfusions.sjwalls.extensions.context.isUpdate
import com.nerdinfusions.sjwalls.ui.fragments.buildChangelogDialog

abstract class BaseChangelogDialogActivity<out P : Preferences> : BaseSearchableActivity<P>() {

    private val changelogDialog: AlertDialog? by lazy { buildChangelogDialog() }

    fun showChangelog(force: Boolean = false) {
        if (isUpdate || force) changelogDialog?.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.changelog) showChangelog(true)
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            changelogDialog?.dismiss()
        } catch (e: Exception) {
        }
    }
}