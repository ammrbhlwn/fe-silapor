package com.example.silapor.data.remote.response

import com.google.gson.annotations.SerializedName

data class FieldResponse(
	@field:SerializedName("data")
	val data: List<DataField>,

	@field:SerializedName("message")
	val message: String
)

data class DataField(
	@field:SerializedName("jam_buka")
	val jamBuka: String,

	@field:SerializedName("jam_tutup")
	val jamTutup: String,

	@field:SerializedName("kota")
	val kota: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("id")
	val id: Int
)
