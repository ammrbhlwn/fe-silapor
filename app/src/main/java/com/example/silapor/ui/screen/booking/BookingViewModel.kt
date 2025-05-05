package com.example.silapor.ui.screen.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silapor.data.FieldRepository
import com.example.silapor.data.remote.response.BookingResponse
import com.example.silapor.data.remote.response.CheckTotalResponse
import com.example.silapor.data.remote.response.FieldDetailResponse
import com.example.silapor.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class BookingViewModel (
    private val repository: FieldRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FieldDetailResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FieldDetailResponse>> = _uiState

    private val _totalHarga = MutableStateFlow<UiState<CheckTotalResponse>>(UiState.Loading)
    val totalHarga: StateFlow<UiState<CheckTotalResponse>> = _totalHarga

    private val _bookingResponse = MutableStateFlow<UiState<BookingResponse>>(UiState.Loading)
    val bookingResponse: StateFlow<UiState<BookingResponse>> = _bookingResponse

    fun getFieldDetail(id: Int) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            _uiState.value = repository.getFieldDetail(id)
        }
    }

    fun checkTotalPrice(lapanganId: Int, jamMulai: String, jamSelesai: String) {
        _totalHarga.value = UiState.Loading

        viewModelScope.launch {
            try {
                val result = repository.getTotalPrice(lapanganId, jamMulai, jamSelesai)
                _totalHarga.value = result
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Gagal cek harga")
            }
        }
    }

    fun createBooking(
        fieldId: Int,
        nama: String,
        nomor: String,
        tanggal: String,
        jamMulai: String,
        jamSelesai: String,
        buktiPembayaran: File
    ) {
        _bookingResponse.value = UiState.Loading

        viewModelScope.launch {
            try {
                _bookingResponse.value = repository.createBooking(
                    fieldId, nama, nomor, tanggal, jamMulai, jamSelesai, buktiPembayaran
                )
            } catch (e: Exception) {
                _bookingResponse.value = UiState.Error(e.message ?: "Gagal membuat booking")
            }
        }
    }
}