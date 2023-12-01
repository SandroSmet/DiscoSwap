package com.example.discoswap.network

import com.example.discoswap.data.ApiMessagesRepository
import com.example.discoswap.data.MessagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class AppRepoModule{
    @Provides
    fun providesMessageRepository(apiService: MessageApiService): MessagesRepository =
        ApiMessagesRepository(apiService)
}

@InstallIn(SingletonComponent::class)
@Module
class ApiServiceModule{
    @Provides
    fun providesMessageApiService(retrofit: Retrofit): MessageApiService =
        MessageApiService.create(retrofit)
}