package com.example.silapor.data

import com.example.silapor.data.remote.response.BookingDetailResponse
import com.example.silapor.data.remote.response.BookingResponse
import com.example.silapor.data.remote.response.CheckTotalResponse
import com.example.silapor.data.remote.response.FieldDetailResponse
import com.example.silapor.data.remote.response.FieldResponse
import com.example.silapor.data.remote.retrofit.ApiService
import com.example.silapor.data.remote.retrofit.CheckTotalPriceRequest
import com.example.silapor.data.remote.retrofit.TransactionRequest
import com.example.silapor.ui.common.UiState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

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

    suspend fun getStatusTransaction(nomor: String, booking_trx_id: String): UiState<BookingDetailResponse> {
        return try {
            val transactionRequest = TransactionRequest(
                booking_trx_id = booking_trx_id,
                nomor = nomor
            )
            val response = apiService.getStatusTransaction(transactionRequest)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error("Transaksi tidak ditemukan")
        }
    }

    suspend fun getTotalPrice(lapanganId: Int, jamMulai: String, jamSelesai: String): UiState<CheckTotalResponse> {
        return try {
            val bodyRequest = CheckTotalPriceRequest(
                lapangan_id = lapanganId,
                jam_mulai = jamMulai,
                jam_selesai = jamSelesai
            )
            val response = apiService.getTotalPrice(bodyRequest)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error("Tidak dapat memproses harga")
        }
    }

    suspend fun getFieldDetail(lapanganId: Int): UiState<FieldDetailResponse> {
        return try {
            val response = apiService.getDetailField(lapanganId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    UiState.Success(body)
                } else {
                    UiState.Error("Data tidak ditemukan")
                }
            } else {
                UiState.Error("Gagal mengambil detail lapangan")
            }
        } catch (e: Exception) {
            UiState.Error("Error")
        }
    }

    suspend fun createBooking(
        fieldId: Int,
        nama: String,
        nomor: String,
        tanggal: String,
        jamMulai: String,
        jamSelesai: String,
        bukti: File
    ): UiState<BookingResponse> {
        return try {
            val requestFile = bukti.asRequestBody("image/*".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData("bukti_pembayaran", bukti.name, requestFile)

            fun String.toRequestBody(): RequestBody =
                this.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = apiService.createBooking(
                fieldId.toString().toRequestBody(),
                nama.toRequestBody(),
                nomor.toRequestBody(),
                tanggal.toRequestBody(),
                jamMulai.toRequestBody(),
                jamSelesai.toRequestBody(),
                multipartBody
            )
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error("Tidak dapat membuat transaksi: ${e.message}")
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