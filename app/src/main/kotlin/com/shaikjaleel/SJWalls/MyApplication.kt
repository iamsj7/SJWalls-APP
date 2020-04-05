package com.shaikjaleel.SJWalls

import com.onesignal.OneSignal
import dev.jahir.frames.ui.FramesApplication

class MyApplication : FramesApplication() {
    override fun onCreate() {
        super.onCreate()
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()
        
    }
}