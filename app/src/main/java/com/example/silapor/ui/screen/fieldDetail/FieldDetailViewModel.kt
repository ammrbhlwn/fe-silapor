package com.example.silapor.ui.screen.fieldDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silapor.data.FieldRepository
import com.example.silapor.data.remote.response.DataFieldDetail
import com.example.silapor.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FieldDetailViewModel (
    private val repository: FieldRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<DataFieldDetail>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DataFieldDetail>> = _uiState

    fun getFieldById(fieldId: Int) {
        viewModelScope.launch {
            when (val result = repository.getDetailFields(fieldId)) {
                is UiState.Loading -> {
                    _uiState.value = UiState.Loading
                }
                is UiState.Success -> {
                    val fieldDetail = result.data.data
                    _uiState.value = UiState.Success(fieldDetail)
                }
                is UiState.Error -> {
                    _uiState.value = UiState.Error(result.error)
                }
            }
        }
    }
}
