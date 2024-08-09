package com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.trap2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class OrdersApi {
    suspend fun postOrder(): String? {
        return try {
            delay(2000)
            val orderNumber = "123456"
            println("Order placed: $orderNumber")
            orderNumber
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            null
        }
    }
}

class OrdersDao {
    suspend fun saveTrackingNumber(trackingNumber: String) {
        delay(2000)
        println("Tracking number saved: $trackingNumber")
    }
}

class OrdersRepository(
    private val api: OrdersApi,
    private val dao: OrdersDao,
    private val applicationScope: CoroutineScope
) {
    suspend fun placeOrder() {
        println("Placing order...")
        val trackingNumber = api.postOrder()
        if (trackingNumber != null) {
            println("Order placed successfully, saving tracking number: $trackingNumber")
            applicationScope.launch {
                dao.saveTrackingNumber(trackingNumber)
            }.join()
        }
    }
}