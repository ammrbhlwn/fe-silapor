package com.example.silapor.data.remote.response

import com.google.gson.annotations.SerializedName

data class BookingDetailResponse(

	@field:SerializedName("data")
	val data: DataBookingDetail,

	@field:SerializedName("message")
	val message: String
)

data class DataBookingDetail(

	@field:SerializedName("jam_mulai")
	val jamMulai: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("lokasi")
	val lokasi: String,

	@field:SerializedName("jam_selesai")
	val jamSelesai: String,

	@field:SerializedName("total_harga")
	val totalHarga: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tanggal_booking")
	val tanggalBooking: String,

	@field:SerializedName("bukti_pembayaran")
	val buktiPembayaran: String,

	@field:SerializedName("status_transaksi")
	val statusTransaksi: String,

	@field:SerializedName("nama_penyewa")
	val namaPenyewa: String,

	@field:SerializedName("nama_lapangan")
	val namaLapangan: String
)
