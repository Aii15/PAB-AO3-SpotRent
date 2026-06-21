package com.pab.spotrent.data.repository

import com.pab.spotrent.R
import com.pab.spotrent.data.model.Property

object PropertyRepository {
    val dummyProperties = listOf(
        Property(
            id = 1,
            name = "Kota Tua Jakarta",
            description = "Kawasan bersejarah dengan arsitektur kolonial yang ikonik.",
            location = "Jakarta Barat",
            price = 15000000,
            rating = 4.9,
            reviews = 70,
            thumbnailRes = R.drawable.prop_default,
            detailImages = listOf(R.drawable.prop_kotatua_2, R.drawable.prop_lawangsewu_1, R.drawable.prop_lawangsewu_2),
            type = "Komersial",
            specifications = listOf("Sanitasi", "CCTV", "Sprinkler Water", "APAR", "Listrik dan Penerangan", "Parkir Mobil", "Permit Included", "Outdoor")
        ),
        Property(
            id = 2,
            name = "Lawang Sewu",
            description = "Bangunan Bersejarah Terkenal Di Kota Semarang, Jawa Tengah.",
            location = "Semarang",
            price = 150000000,
            rating = 4.9,
            reviews = 70,
            thumbnailRes = R.drawable.prop_lawangsewu_1,
            detailImages = listOf(R.drawable.prop_lawangsewu_2, R.drawable.prop_lawangsewu_3),
            type = "Heritage",
            specifications = listOf("Sanitasi", "CCTV", "Sprinkler Water", "APAR", "Listrik dan Penerangan", "Parkir Mobil", "Permit Included", "Outdoor")
        ),
        Property(
            id = 3,
            name = "Studio Minimalis",
            description = "Studio modern dengan pencahayaan alami yang luar biasa.",
            location = "Jakarta Selatan",
            price = 5000000,
            rating = 4.8,
            reviews = 45,
            thumbnailRes = R.drawable.prop_studio_1,
            detailImages = listOf(R.drawable.prop_studio_2, R.drawable.prop_studio_1),
            type = "Studio",
            specifications = listOf("AC", "WiFi", "Listrik", "Ruang Ganti")
        ),
        Property(
            id = 4,
            name = "Villa Puncak",
            description = "Villa luas dengan pemandangan gunung yang asri.",
            location = "Bogor",
            price = 12000000,
            rating = 4.7,
            reviews = 30,
            thumbnailRes = R.drawable.prop_villa_puncak_1,
            detailImages = listOf(R.drawable.prop_villa_puncak_2, R.drawable.prop_villa_puncak_1),
            type = "Hunian",
            specifications = listOf("Kolam Renang", "Dapur", "Parkir", "Pemandangan")
        ),
        Property(
            id = 5,
            name = "Lanskap Sawah",
            description = "Area terbuka hijau yang luas untuk kebutuhan syuting bertema alam.",
            location = "Bali",
            price = 8000000,
            rating = 5.0,
            reviews = 20,
            thumbnailRes = R.drawable.prop_lanskapsawah_1,
            detailImages = listOf(R.drawable.prop_lanskapsawah_2, R.drawable.prop_lanskapsawah_1),
            type = "Lanskap",
            specifications = listOf("Akses Jalan", "Listrik Genset", "Pemandangan Alam")
        )
    )

    fun getPropertyById(id: Int): Property? {
        return dummyProperties.find { it.id == id }
    }
}
