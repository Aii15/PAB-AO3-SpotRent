package com.pab.spotrent.data.repository

import com.pab.spotrent.data.model.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object ReviewRepository {
    private val _reviews = MutableStateFlow<List<Review>>(listOf(
        // Dummy reviews for Kota Tua Jakarta (Property 1)
        Review(
            id = "REV001",
            propertyId = 1,
            bookingId = null,
            userName = "Ahmad Dani",
            userAvatarText = "A",
            date = "15 Juni 2026",
            rating = 5,
            comment = "Tempatnya sangat ikonik dan terawat. Sangat direkomendasikan untuk syuting iklan tempo dulu!"
        ),
        Review(
            id = "REV002",
            propertyId = 1,
            bookingId = null,
            userName = "Budi Santoso",
            userAvatarText = "B",
            date = "18 Juni 2026",
            rating = 4,
            comment = "Kawasan yang luas dan suasana yang sangat mendukung. Izin operasional juga mudah dibantu."
        ),
        // Dummy reviews for Lawang Sewu (Property 2)
        Review(
            id = "REV003",
            propertyId = 2,
            bookingId = null,
            userName = "Citra Lestari",
            userAvatarText = "C",
            date = "10 Juni 2026",
            rating = 5,
            comment = "Sangat bersejarah dan atmosfernya luar biasa. Foto-foto di sini hasilnya estetik sekali!"
        ),
        Review(
            id = "REV004",
            propertyId = 2,
            bookingId = null,
            userName = "Dedi Wijaya",
            userAvatarText = "D",
            date = "12 Juni 2026",
            rating = 5,
            comment = "Pelayanan dari PT Kereta Api Wisata sangat profesional. Listrik dan kelengkapan apar tersedia lengkap."
        ),
        // Dummy reviews for Studio Minimalis (Property 3)
        Review(
            id = "REV005",
            propertyId = 3,
            bookingId = null,
            userName = "Eka Putra",
            userAvatarText = "E",
            date = "05 Juni 2026",
            rating = 5,
            comment = "Pencahayaan alami dari jendela besarnya juara! Bersih, rapi, cocok untuk podcast atau video produk."
        ),
        Review(
            id = "REV006",
            propertyId = 3,
            bookingId = null,
            userName = "Fanya Olivia",
            userAvatarText = "F",
            date = "07 Juni 2026",
            rating = 4,
            comment = "Fasilitas studio lengkap, AC dingin, dan lokasinya strategis di Jakarta Selatan."
        ),
        // Dummy reviews for Villa Puncak (Property 4)
        Review(
            id = "REV007",
            propertyId = 4,
            bookingId = null,
            userName = "Gita Amanda",
            userAvatarText = "G",
            date = "20 Juni 2026",
            rating = 4,
            comment = "Udaranya segar sekali dengan pemandangan gunung. Cocok untuk syuting adegan drama keluarga."
        ),
        Review(
            id = "REV008",
            propertyId = 4,
            bookingId = null,
            userName = "Heri Prasetyo",
            userAvatarText = "H",
            date = "22 Juni 2026",
            rating = 5,
            comment = "Villa sangat luas, kolam renang bersih, dan privasi terjaga dengan baik."
        ),
        // Dummy reviews for Lanskap Sawah (Property 5)
        Review(
            id = "REV009",
            propertyId = 5,
            bookingId = null,
            userName = "Indra Kusuma",
            userAvatarText = "I",
            date = "14 Juni 2026",
            rating = 5,
            comment = "Pemandangan sawah Bali yang magis saat matahari terbit. Izin lokal dibantu oleh pengelola."
        ),
        Review(
            id = "REV010",
            propertyId = 5,
            bookingId = null,
            userName = "Joko Susilo",
            userAvatarText = "J",
            date = "19 Juni 2026",
            rating = 5,
            comment = "Tempat terbuka hijau yang tenang dan sangat indah. Sangat memuaskan!"
        )
    ))
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    fun getReviewsForProperty(propertyId: Int): kotlinx.coroutines.flow.Flow<List<Review>> {
        return reviews.map { list -> list.filter { it.propertyId == propertyId } }
    }

    fun hasUserReviewedBooking(bookingId: String): Boolean {
        return _reviews.value.any { it.bookingId == bookingId }
    }

    fun getReviewByBookingId(bookingId: String): Review? {
        return _reviews.value.find { it.bookingId == bookingId }
    }

    fun addReview(propertyId: Int, bookingId: String?, userName: String, rating: Int, comment: String) {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        val dateString = dateFormat.format(Date())
        
        val newReview = Review(
            id = UUID.randomUUID().toString(),
            propertyId = propertyId,
            bookingId = bookingId,
            userName = userName,
            userAvatarText = if (userName.isNotEmpty()) userName.take(1).uppercase() else "A",
            date = dateString,
            rating = rating,
            comment = comment
        )
        
        val currentList = _reviews.value.toMutableList()
        currentList.add(0, newReview)
        _reviews.value = currentList

        // Update property rating and total reviews dynamically in PropertyRepository
        val property = PropertyRepository.getPropertyById(propertyId)
        if (property != null) {
            val currentReviews = property.reviews
            val currentRating = property.rating
            
            val newReviews = currentReviews + 1
            val newRating = ((currentRating * currentReviews) + rating) / newReviews
            // Round to 1 decimal place, e.g. 4.87 -> 4.9
            val roundedRating = Math.round(newRating * 10.0) / 10.0
            
            PropertyRepository.updatePropertyRating(propertyId, roundedRating, newReviews)
        }
    }
}
