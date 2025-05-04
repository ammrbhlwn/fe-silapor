package com.example.silapor.ui.screen.home

import androidx.lifecycle.ViewModel
import com.example.silapor.model.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel() : ViewModel() {
    private val _selectedSport = MutableStateFlow<String?>(null)
    val selectedSport: StateFlow<String?> = _selectedSport

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity

    fun setSelectedSport(sport: String) {
        _selectedSport.value = sport
    }

    fun setSelectedCity(city: City) {
        _selectedCity.value = city
    }
}