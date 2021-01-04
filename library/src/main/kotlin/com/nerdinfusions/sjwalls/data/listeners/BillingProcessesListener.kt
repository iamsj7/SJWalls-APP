package com.nerdinfusions.sjwalls.data.listeners

import com.android.billingclient.api.SkuDetails
import com.nerdinfusions.sjwalls.data.models.DetailedPurchaseRecord

interface BillingProcessesListener {
    fun onBillingClientReady() {}
    fun onBillingClientDisconnected() {}
    fun onInAppSkuDetailsListUpdated(skuDetailsList: List<SkuDetails>) {}
    fun onSubscriptionsSkuDetailsListUpdated(skuDetailsList: List<SkuDetails>) {}
    fun onInAppPurchasesHistoryUpdated(inAppPurchasesHistory: List<DetailedPurchaseRecord>) {}
    fun onSubscriptionsPurchasesHistoryUpdated(subscriptionsPurchasesHistory: List<DetailedPurchaseRecord>) {}
    fun onSkuPurchaseSuccess(purchase: DetailedPurchaseRecord? = null)
    fun onSkuPurchaseError(purchase: DetailedPurchaseRecord? = null)
}