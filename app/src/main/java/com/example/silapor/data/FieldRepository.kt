package com.example.silapor.data

import com.example.silapor.data.remote.response.BookingDetailResponse
import com.example.silapor.data.remote.response.BookingResponse
import com.example.silapor.data.remote.response.FieldDetailResponse
import com.example.silapor.data.remote.response.FieldResponse
import com.example.silapor.data.remote.retrofit.ApiService
import com.example.silapor.ui.common.UiState

class FieldRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun getAllFutsalFields(): UiState<FieldResponse> {
        return try {
            val response = apiService.getAllFutsalFields()
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error("Gagal mengambil data lapangan futsal: ${e.message}")
        }
    }

    suspend fun getAllBadmintonlFields(): UiState<FieldResponse> {
        return try {
            val response = apiService.getAllBadmintonFields()
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error("Gagal mengambil data lapangan badminton: ${e.message}")
        }
    }

    suspend fun getDetailFields(id: Int) : UiState<FieldDetailResponse> {
        return try {
            val response = apiService.getDetailField(id)
            if (response.isSuccessful && response.body() != null) {
                UiState.Success(response.body()!!)
            } else {
                UiState.Error("Terjadi kesalahan: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            UiState.Error("Gagal mengambil data lapangan: ${e.message}")
        }
    }

    suspend fun getAllBookings(): UiState<BookingResponse> {
        return try {
            val response = apiService.getAllBookings()
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error("Gagal mengambil data booking: ${e.message}")
        }
    }

    suspend fun getDetailBookings(id: Int): UiState<BookingDetailResponse> {
        return try {
            val response = apiService.getDetailBooking(id)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error("Gagal mengambil detail booking: ${e.message}")
        }
    }

    suspend fun getSearchEvent(kota: String): UiState<FieldResponse> {
        return try {
            val response = apiService.getSearchFields(kota)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error("Lapangan di kota $kota tidak ditemukan: ${e.message}")
        }
    }

    companion object {
        @Volatile
        private var instance: FieldRepository? = null
        fun getInstance(
            apiService: ApiService
        ): FieldRepository =
            instance ?: synchronized(this) {
                instance ?: FieldRepository(apiService)
            }.also { instance = it }
    }
}