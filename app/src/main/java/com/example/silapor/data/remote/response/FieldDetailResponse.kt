package com.example.silapor.data.remote.response

import com.google.gson.annotations.SerializedName

data class FieldDetailResponse(

	@field:SerializedName("data")
	val data: DataFieldDetail,

	@field:SerializedName("message")
	val message: String
)

data class DataFieldDetail(

	@field:SerializedName("jam_buka")
	val jamBuka: String,

	@field:SerializedName("jam_tutup")
	val jamTutup: String,

	@field:SerializedName("jadwal")
	val jadwal: Map<String, List<JamTersedia>>,

	@field:SerializedName("kota")
	val kota: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("lokasi")
	val lokasi: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("link_lokasi")
	val linkLokasi: String,
)

data class JamTersedia(
	@field:SerializedName("jam")
	val jam: String,
	@field:SerializedName("jadwal_tersedia")
	val jadwalTersedia: String
)