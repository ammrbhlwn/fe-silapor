package com.example.silapor.data.remote.response

import com.google.gson.annotations.SerializedName

data class CheckTotalResponse(

	@field:SerializedName("data")
	val data: DataTotal,

	@field:SerializedName("message")
	val message: String
)

data class DataTotal(

	@field:SerializedName("jumlah_jam")
	val jumlahJam: Int,

	@field:SerializedName("harga_per_jam")
	val hargaPerJam: Int,

	@field:SerializedName("total_harga")
	val totalHarga: Int
)
