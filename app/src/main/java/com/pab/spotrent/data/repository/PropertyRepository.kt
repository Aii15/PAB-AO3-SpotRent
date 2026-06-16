package com.pab.spotrent.data.repository

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
            imageUrl = "https://images.unsplash.com/photo-1596422846543-75c6fc18a593?q=80&w=1000&auto=format&fit=crop",
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
            imageUrl = "https://images.unsplash.com/photo-1626021469790-27f101188339?q=80&w=1000&auto=format&fit=crop",
            type = "Heritage",
            specifications = listOf("Sanitasi", "CCTV", "Sprinkler Water", "APAR", "Listrik dan Penerangan", "Parkir Mobil", "Permit Included", "Outdoor")
        )
    )

    fun getPropertyById(id: Int): Property? {
        return dummyProperties.find { it.id == id }
    }
}
