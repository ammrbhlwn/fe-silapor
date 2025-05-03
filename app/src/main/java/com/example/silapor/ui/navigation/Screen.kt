package com.example.silapor.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object StatusTransaksi : Screen("statusTransaksi")
    data object Booking : Screen("field/{sportType}") {
        fun createRoute(sportType: String) = "field/$sportType"
    }
    data object DetailField : Screen("field/{fieldId}") {
        fun createRoute(fieldId: Int) = "field/$fieldId"
    }
    data object BookingDetail : Screen("field/{fieldId}") {
        fun createRoute(fieldId: Int) = "field/$fieldId"
    }
}
