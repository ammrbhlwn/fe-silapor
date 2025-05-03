package com.example.silapor.ui.screen.fieldList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silapor.data.FieldRepository
import com.example.silapor.data.remote.response.DataField
import com.example.silapor.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FieldListViewModel(
    private val repository: FieldRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<DataField>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<DataField>>> = _uiState

    fun getFieldsByType(sportType: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = when (sportType.lowercase()) {
                "futsal" -> repository.getAllFutsalFields()
                "badminton" -> repository.getAllBadmintonlFields()
                else -> UiState.Error("Invalid sport type")
            }

            _uiState.value = when (result) {
                is UiState.Success -> UiState.Success(result.data.data)
                is UiState.Error -> result
                else -> UiState.Error("Unknown error")
            }
        }
    }
}