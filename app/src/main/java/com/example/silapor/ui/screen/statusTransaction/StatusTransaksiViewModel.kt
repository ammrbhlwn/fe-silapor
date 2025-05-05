package com.example.silapor.ui.screen.statusTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silapor.data.FieldRepository
import com.example.silapor.data.remote.response.DataBookingDetail
import com.example.silapor.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatusTransaksiViewModel(
    private val repository: FieldRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<DataBookingDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<DataBookingDetail>> = _uiState

    fun checkTransactionStatus(nomor: String, bookingTrxId: String) {
        viewModelScope.launch {
            when (val result = repository.getStatusTransaction(nomor, bookingTrxId)) {
                is UiState.Loading -> {
                    _uiState.value = UiState.Loading
                }
                is UiState.Success -> {
                    val transactionStatus = result.data.data
                    _uiState.value = UiState.Success(transactionStatus)
                }
                is UiState.Error -> {
                    _uiState.value = UiState.Error(result.error)
                }
                is UiState.Empty -> {}
            }
        }
    }
}
