package com.example.silapor.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object StatusTransaksi : Screen("statusTransaksi")
    data object FieldList : Screen("field/{sportType}") {
        fun createRoute(sportType: String) = "field/$sportType"
    }
    data object Booking : Screen("field/{fieldId}") {
        fun createRoute(fieldId: Int) = "field/$fieldId"
    }
}
