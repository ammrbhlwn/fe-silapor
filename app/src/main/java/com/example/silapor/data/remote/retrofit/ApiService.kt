package com.example.silapor.data.remote.retrofit

import com.example.silapor.data.remote.response.BookingDetailResponse
import com.example.silapor.data.remote.response.BookingResponse
import com.example.silapor.data.remote.response.CheckTotalResponse
import com.example.silapor.data.remote.response.FieldDetailResponse
import com.example.silapor.data.remote.response.FieldResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

data class TransactionRequest(
    val booking_trx_id: String,
    val nomor: String
)

data class CheckTotalPriceRequest(
    val lapangan_id: Int,
    val jam_mulai: String,
    val jam_selesai: String
)

interface ApiService {
    @GET("list/lapangan/futsal")
    suspend fun getAllFutsalFields(
    ): FieldResponse

    @GET("list/lapangan/badminton")
    suspend fun getAllBadmintonFields(
    ): FieldResponse

    @GET("lapangan/{id}")
    suspend fun getDetailField(
        @Path("id") id: Int
    ): Response<FieldDetailResponse>

    @GET("search")
    suspend fun getSearchFields(
        @Query("kota") query: String? = null,
    ): FieldResponse

    @GET("user/booking/list")
    suspend fun getAllBookings(
    ): BookingResponse

    @GET("user/booking/{id}")
    suspend fun getDetailBooking(
        @Path("id") id: Int
    ): BookingDetailResponse

    @POST("user/check/booking")
    suspend fun getStatusTransaction(
        @Body request: TransactionRequest
    ): BookingDetailResponse

    @POST("user/check/total")
    suspend fun getTotalPrice(
        @Body request: CheckTotalPriceRequest
    ): CheckTotalResponse

    @Multipart
    @POST("user/booking")
    suspend fun createBooking(
        @Part("lapangan_id") fieldId: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("nomor") nomor: RequestBody,
        @Part("tanggal_booking") tanggal: RequestBody,
        @Part("jam_mulai") jamMulai: RequestBody,
        @Part("jam_selesai") jamSelesai: RequestBody,
        @Part bukti: MultipartBody.Part
    ): BookingResponse

}