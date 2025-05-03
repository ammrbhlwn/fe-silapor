package com.example.silapor.data.remote.response

import com.google.gson.annotations.SerializedName

data class BookingResponse(

	@field:SerializedName("data")
	val data: List<DataBooking>,

	@field:SerializedName("message")
	val message: String
)

data class DataBooking(

	@field:SerializedName("jam_mulai")
	val jamMulai: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("jam_selesai")
	val jamSelesai: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tanggal_booking")
	val tanggalBooking: String,

	@field:SerializedName("status_transaksi")
	val statusTransaksi: String,

	@field:SerializedName("nama_lapangan")
	val namaLapangan: String
)
