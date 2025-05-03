package com.example.silapor.ui.common

sealed class UiState<out R> private constructor() {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val error: String) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
}