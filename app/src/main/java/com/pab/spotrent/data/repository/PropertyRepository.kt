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
        ),
        Property(
            id = 3,
            name = "Studio Minimalis",
            description = "Studio modern dengan pencahayaan alami yang luar biasa.",
            location = "Jakarta Selatan",
            price = 5000000,
            rating = 4.8,
            reviews = 45,
            imageUrl = "https://images.unsplash.com/photo-1598425237654-4fc758e50a93?q=80&w=1000&auto=format&fit=crop",
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
            imageUrl = "https://images.unsplash.com/photo-1580587767516-24e531818223?q=80&w=1000&auto=format&fit=crop",
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
            imageUrl = "https://images.unsplash.com/photo-1537996194471-e657df975ab4?q=80&w=1000&auto=format&fit=crop",
            type = "Lanskap",
            specifications = listOf("Akses Jalan", "Listrik Genset", "Pemandangan Alam")
        )
    )

    fun getPropertyById(id: Int): Property? {
        return dummyProperties.find { it.id == id }
    }
}
