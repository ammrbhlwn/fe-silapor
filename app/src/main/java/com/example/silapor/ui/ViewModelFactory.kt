package com.example.silapor.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.silapor.data.FieldRepository
import com.example.silapor.ui.screen.booking.BookingViewModel
import com.example.silapor.ui.screen.fieldList.FieldListViewModel
import com.example.silapor.ui.screen.home.HomeViewModel
import com.example.silapor.ui.screen.statusTransaction.StatusTransaksiViewModel
import kotlin.jvm.java

class ViewModelFactory(private val repository: FieldRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        } else if (modelClass.isAssignableFrom(FieldListViewModel::class.java)) {
            return FieldListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(StatusTransaksiViewModel::class.java)) {
            return StatusTransaksiViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(BookingViewModel::class.java)) {
            return BookingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}