package com.nerdinfusions.sjwalls.ui.activities.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import com.nerdinfusions.sjwalls.R
import com.nerdinfusions.sjwalls.data.Preferences
import com.nerdinfusions.sjwalls.extensions.context.color
import com.nerdinfusions.sjwalls.extensions.context.getRightNavigationBarColor
import com.nerdinfusions.sjwalls.extensions.context.navigationBarColor
import com.nerdinfusions.sjwalls.extensions.context.navigationBarLight
import com.nerdinfusions.sjwalls.extensions.context.resolveColor
import com.nerdinfusions.sjwalls.extensions.context.restart
import com.nerdinfusions.sjwalls.extensions.context.statusBarColor
import com.nerdinfusions.sjwalls.extensions.context.statusBarLight
import com.nerdinfusions.sjwalls.extensions.resources.isDark
import com.nerdinfusions.sjwalls.extensions.utils.postDelayed

abstract class BaseThemedActivity<out P : Preferences> : BaseFinishResultActivity() {

    private var wasUsingAmoled: Boolean = false
    private var coloredNavbar: Boolean = false

    @StyleRes
    open fun defaultTheme(): Int = R.style.Frames_Default

    @StyleRes
    open fun amoledTheme(): Int = R.style.Frames_Default_Amoled

    abstract val preferences: P

    override fun onCreate(savedInstanceState: Bundle?) {
        setCustomTheme()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (wasUsingAmoled != preferences.usesAmoledTheme
            || coloredNavbar != preferences.shouldColorNavbar)
            onThemeChanged()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        wasUsingAmoled = preferences.usesAmoledTheme
        coloredNavbar = preferences.shouldColorNavbar
    }

    internal fun onThemeChanged() {
        postDelayed(2) { restart() }
    }

    @Suppress("DEPRECATION")
    private fun setCustomTheme() {
        setTheme(if (preferences.usesAmoledTheme) amoledTheme() else defaultTheme())
        resolveColor(R.attr.colorPrimaryDark, color(R.color.primaryDark)).let {
            statusBarColor = it
            if (shouldChangeStatusBarLightStatus)
                statusBarLight = !it.isDark
        }
        getRightNavigationBarColor().let {
            navigationBarColor = it
            if (shouldChangeNavigationBarLightStatus)
                navigationBarLight = !it.isDark
        }
    }

    fun replaceFragment(
        fragment: Fragment?,
        fragmentTag: String = "fragment",
        @IdRes fragmentContainerId: Int = R.id.fragments_container,
        animate: Boolean = true
    ) {
        fragment ?: return
        try {
            val transaction = supportFragmentManager.beginTransaction()
            if (animate && preferences.animationsEnabled) {
                transaction.setCustomAnimations(
                    R.anim.fragment_fade_in, R.anim.fragment_fade_out,
                    R.anim.fragment_fade_in, R.anim.fragment_fade_out
                )
            }
            transaction
                .replace(fragmentContainerId, fragment, fragmentTag)
                .commit()
        } catch (e: Exception) {
            Log.e("Frames", e.message, e)
        }
    }

    open val shouldChangeStatusBarLightStatus: Boolean = true
    open val shouldChangeNavigationBarLightStatus: Boolean = true
}