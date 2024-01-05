package com.example.discoswap.network.message

import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit service interface for interacting with the message-related endpoints of the remote API.
 */
interface MessageApiService {
    /**
     * Fetches the user's messages from the remote API.
     *
     * @return an [ApiMessages] representing the user's messages
     */
    @GET("messages?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi&per_page=100")
    suspend fun getMessages(): ApiMessages

    /**
     * Fetches details for a specific message from the remote API.
     *
     * @param id the unique identifier of the message
     * @return an [ApiMessageItem] representing the details of the specified message
     */
    @GET("messages/{id}?token=mnwalUhcJspcuQYpcIYCWLWYNWSgaDBgdtQQRmNi")
    suspend fun getMessageDetails(@Path("id") id: String): ApiMessageItem

}

/**
 * Extension function to convert the [MessageApiService.getMessages] suspend function to a flow.
 *
 * @receiver the [MessageApiService] instance
 * @return a Flow emitting the result of [MessageApiService.getMessages]
 */
fun MessageApiService.getMessagesAsFlow() = flow { emit(getMessages()) }

/**
 * Extension function to convert the [MessageApiService.getMessageDetails] suspend function to a flow.
 *
 * @receiver the [MessageApiService] instance
 * @param id the unique identifier of the message
 * @return a Flow emitting the result of [MessageApiService.getMessageDetails] with the specified [id]
 */
fun MessageApiService.getMessageDetailsAsFlow(id: String) = flow { emit(getMessageDetails(id)) }
