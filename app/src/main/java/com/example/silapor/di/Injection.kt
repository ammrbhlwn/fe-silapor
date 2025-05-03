package com.example.silapor.di

import com.example.silapor.data.FieldRepository
import com.example.silapor.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(): FieldRepository {
        val apiService = ApiConfig.getApiService()
        return FieldRepository.getInstance(apiService)
    }
}