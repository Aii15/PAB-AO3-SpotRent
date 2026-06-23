package com.pab.spotrent.data.repository

import android.content.Context
import com.pab.spotrent.data.database.UserDatabaseHelper
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
    private var dbHelper: UserDatabaseHelper? = null

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    fun initialize(context: Context) {
        dbHelper = UserDatabaseHelper(context.applicationContext)
        loadReviewsFromDb()
    }

    private fun loadReviewsFromDb() {
        val helper = dbHelper ?: return
        val list = helper.getAllReviews()
        // Reverse to have newest reviews first
        val sortedList = list.reversed()
        _reviews.value = sortedList

        // Update property ratings for user-submitted reviews at startup
        sortedList.forEach { review ->
            if (review.bookingId != null) {
                val property = PropertyRepository.getPropertyById(review.propertyId)
                if (property != null) {
                    val currentReviews = property.reviews
                    val currentRating = property.rating
                    val newReviews = currentReviews + 1
                    val newRating = ((currentRating * currentReviews) + review.rating) / newReviews
                    val roundedRating = Math.round(newRating * 10.0) / 10.0
                    PropertyRepository.updatePropertyRating(review.propertyId, roundedRating, newReviews)
                }
            }
        }
    }

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
        val helper = dbHelper ?: return
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
        
        // 1. Save to database
        helper.addReview(newReview)

        // 2. Prepend to state flow list
        val currentList = _reviews.value.toMutableList()
        currentList.add(0, newReview)
        _reviews.value = currentList

        // 3. Update property rating and total reviews dynamically in PropertyRepository
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
