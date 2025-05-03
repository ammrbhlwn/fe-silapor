package com.example.silapor.model

object DummyFieldData {
    val cities = listOf(
        City(1, "Jakarta"),
        City(2, "Bandung"),
    )

    val sports = listOf(
        Sport( "Futsal", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBmlia5oYYzYlbD9aP0UIzlFs08Do2zUcrMw&s"),
        Sport( "Badminton", "https://asset.ayo.co.id/image/venue/168398058571403.image_cropper_1683980470943_large.jpg")
    )

    val fields = listOf(
        // Jakarta
        Field(
            1,
            1,
            "Futsal",
            "Futsal Kemang",
            "Jl. Kemang Raya No.5, Jakarta",
            200000,
            "https://example.com/futsal-kemang.jpg",
            7,
            21,
            "google.com"
        ),

        Field(
            10,
            1,
            "Badminton",
            "GOR Tanjung Priok",
            "Jl. Enggano No.5, Jakarta",
            125000,
            "https://example.com/gor-priok.jpg",
            8,
            22,
            "google.com"
        ),

        // Bandung
        Field(
            11,
            2,
            "Futsal",
            "Futsal Galaxy Bandung",
            "Jl. Soekarno Hatta No.25, Bandung",
            150000,
            "https://example.com/galaxy-bandung.jpg",
            10,
            22,
            "google.com"
        ),

        Field(
            20,
            2,
            "Badminton",
            "GOR Cimahi",
            "Jl. Kolonel Masturi No.1, Cimahi",
            95000,
            "https://example.com/gor-cimahi.jpg",
            10,
            23,
            "google.com"
        ),
    )
}