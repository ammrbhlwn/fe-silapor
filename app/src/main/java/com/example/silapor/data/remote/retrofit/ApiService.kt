package com.example.silapor.data.remote.retrofit

import com.example.silapor.data.remote.response.BookingDetailResponse
import com.example.silapor.data.remote.response.BookingResponse
import com.example.silapor.data.remote.response.FieldDetailResponse
import com.example.silapor.data.remote.response.FieldResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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
}