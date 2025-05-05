package com.example.silapor.data.remote.response

import com.google.gson.annotations.SerializedName

data class BookingResponse(

	@field:SerializedName("data")
	val data: DataBooking,

	@field:SerializedName("message")
	val message: String
)

data class DataBooking(

	@field:SerializedName("booking_trx_id")
	val bookingTrxId: String,

	@field:SerializedName("status_transaksi")
	val statusTransaksi: String
)
