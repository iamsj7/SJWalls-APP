package com.nerdinfusions.sjwalls.app

import com.nerdinfusions.sjwalls.extensions.context.preferences
import com.onesignal.NotificationExtenderService
import com.onesignal.OSNotificationReceivedResult

class NotificationService : NotificationExtenderService() {
    override fun onNotificationProcessing(notification: OSNotificationReceivedResult?): Boolean =
        !preferences.notificationsEnabled
}
