package com.pab.spotrent.data.repository

import com.pab.spotrent.R
import com.pab.spotrent.data.model.Property
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PropertyRepository {
    private val _properties = MutableStateFlow<List<Property>>(listOf(
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
            specifications = listOf("Outdoor", "Permit Included", "Parkir Mobil", "CCTV", "Sanitasi"),
            partnerName = "UPT Kota Tua Jakarta",
            partnerLogoText = "UPT"
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
            specifications = listOf("Outdoor", "Permit Included", "Listrik dan Penerangan", "APAR", "CCTV", "Sanitasi", "Sprinkler Water"),
            partnerName = "PT. Kereta Api Wisata",
            partnerLogoText = "KAI"
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
            specifications = listOf("Listrik dan Penerangan", "APAR", "Sanitasi", "CCTV"),
            partnerName = "Creative Space Studio",
            partnerLogoText = "CSS"
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
            specifications = listOf("Outdoor", "Sanitasi", "Parkir Mobil", "APAR", "CCTV"),
            partnerName = "Villa Management Group",
            partnerLogoText = "VMG"
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
            specifications = listOf("Outdoor", "Permit Included", "Parkir Mobil"),
            partnerName = "Balinese Nature Project",
            partnerLogoText = "BNP"
        )
    ))

    val properties: StateFlow<List<Property>> = _properties.asStateFlow()

    val dummyProperties: List<Property>
        get() = _properties.value

    fun getPropertyById(id: Int): Property? {
        return _properties.value.find { it.id == id }
    }

    fun updatePropertyRating(id: Int, newRating: Double, newReviewsCount: Int) {
        _properties.value = _properties.value.map {
            if (it.id == id) {
                it.copy(rating = newRating, reviews = newReviewsCount)
            } else {
                it
            }
        }
    }
}
