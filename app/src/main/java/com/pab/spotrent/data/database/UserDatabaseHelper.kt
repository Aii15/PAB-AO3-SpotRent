package com.pab.spotrent.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pab.spotrent.R
import com.pab.spotrent.data.model.User
import java.util.Calendar
import com.pab.spotrent.data.model.Booking
import com.pab.spotrent.data.model.Review

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "spotrent.db"
        private const val DATABASE_VERSION = 5 // Incremented for reviews schema change

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_FULL_NAME = "fullName"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_PHONE = "phone"

        const val TABLE_WISHLIST = "wishlist"
        const val COLUMN_WISHLIST_ID = "id"
        const val COLUMN_WISHLIST_USER_ID = "userId"
        const val COLUMN_WISHLIST_PROPERTY_ID = "propertyId"

        const val TABLE_BOOKINGS = "bookings"
        const val COLUMN_BOOKING_ID = "id"
        const val COLUMN_BOOKING_USER_ID = "userId"
        const val COLUMN_BOOKING_PROPERTY_ID = "propertyId"
        const val COLUMN_BOOKING_PROPERTY_NAME = "propertyName"
        const val COLUMN_BOOKING_PROPERTY_LOCATION = "propertyLocation"
        const val COLUMN_BOOKING_PROPERTY_THUMBNAIL = "propertyThumbnail"
        const val COLUMN_BOOKING_START_DATE = "startDate"
        const val COLUMN_BOOKING_END_DATE = "endDate"
        const val COLUMN_BOOKING_TOTAL_PRICE = "totalPrice"
        const val COLUMN_BOOKING_STATUS = "status"
        const val COLUMN_BOOKING_PAYMENT_METHOD = "paymentMethod"

        const val TABLE_REVIEWS = "reviews"
        const val COLUMN_REVIEW_ID = "id"
        const val COLUMN_REVIEW_PROPERTY_ID = "propertyId"
        const val COLUMN_REVIEW_BOOKING_ID = "bookingId"
        const val COLUMN_REVIEW_USER_NAME = "userName"
        const val COLUMN_REVIEW_USER_AVATAR_TEXT = "userAvatarText"
        const val COLUMN_REVIEW_DATE = "date"
        const val COLUMN_REVIEW_RATING = "rating"
        const val COLUMN_REVIEW_COMMENT = "comment"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT UNIQUE,
                $COLUMN_EMAIL TEXT UNIQUE,
                $COLUMN_FULL_NAME TEXT,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_PHONE TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)

        val createWishlistTableQuery = """
            CREATE TABLE $TABLE_WISHLIST (
                $COLUMN_WISHLIST_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_WISHLIST_USER_ID INTEGER,
                $COLUMN_WISHLIST_PROPERTY_ID INTEGER,
                UNIQUE($COLUMN_WISHLIST_USER_ID, $COLUMN_WISHLIST_PROPERTY_ID)
            )
        """.trimIndent()
        db.execSQL(createWishlistTableQuery)

        val createBookingTableQuery = """
            CREATE TABLE $TABLE_BOOKINGS (
                $COLUMN_BOOKING_ID TEXT PRIMARY KEY,
                $COLUMN_BOOKING_USER_ID INTEGER,
                $COLUMN_BOOKING_PROPERTY_ID INTEGER,
                $COLUMN_BOOKING_PROPERTY_NAME TEXT,
                $COLUMN_BOOKING_PROPERTY_LOCATION TEXT,
                $COLUMN_BOOKING_PROPERTY_THUMBNAIL INTEGER,
                $COLUMN_BOOKING_START_DATE INTEGER,
                $COLUMN_BOOKING_END_DATE INTEGER,
                $COLUMN_BOOKING_TOTAL_PRICE INTEGER,
                $COLUMN_BOOKING_STATUS TEXT,
                $COLUMN_BOOKING_PAYMENT_METHOD TEXT
            )
        """.trimIndent()
        db.execSQL(createBookingTableQuery)

        val createReviewTableQuery = """
            CREATE TABLE $TABLE_REVIEWS (
                $COLUMN_REVIEW_ID TEXT PRIMARY KEY,
                $COLUMN_REVIEW_PROPERTY_ID INTEGER,
                $COLUMN_REVIEW_BOOKING_ID TEXT,
                $COLUMN_REVIEW_USER_NAME TEXT,
                $COLUMN_REVIEW_USER_AVATAR_TEXT TEXT,
                $COLUMN_REVIEW_DATE TEXT,
                $COLUMN_REVIEW_RATING INTEGER,
                $COLUMN_REVIEW_COMMENT TEXT
            )
        """.trimIndent()
        db.execSQL(createReviewTableQuery)

        // Insert default dummy users
        insertDummyUser(db, 1, "admin", "admin@spotrent.com", "Admin SpotRent", "password123", "081234567890")
        insertDummyUser(db, 2, "user", "user@gmail.com", "John Doe", "password123", "08137465830")

        // Insert dummy bookings
        val dummyStart = Calendar.getInstance().apply {
            set(2026, Calendar.JULY, 2, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val dummyEnd = Calendar.getInstance().apply {
            set(2026, Calendar.JULY, 4, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        insertDummyBooking(db, "BKG001", 1, 1, "Kota Tua Jakarta", "Jakarta Barat", R.drawable.prop_default, dummyStart, dummyEnd, 45000000L, "Berhasil", "Transfer Bank")
        insertDummyBooking(db, "BKG001", 2, 1, "Kota Tua Jakarta", "Jakarta Barat", R.drawable.prop_default, dummyStart, dummyEnd, 45000000L, "Berhasil", "Transfer Bank")

        // Insert dummy reviews
        insertDummyReview(db, "REV001", 1, null, "Ahmad Dani", "A", "15 Juni 2026", 5, "Tempatnya sangat ikonik dan terawat. Sangat direkomendasikan untuk syuting iklan tempo dulu!")
        insertDummyReview(db, "REV002", 1, null, "Budi Santoso", "B", "18 Juni 2026", 4, "Kawasan yang luas dan suasana yang sangat mendukung. Izin operasional juga mudah dibantu.")
        insertDummyReview(db, "REV003", 2, null, "Citra Lestari", "C", "10 Juni 2026", 5, "Sangat bersejarah dan atmosfernya luar biasa. Foto-foto di sini hasilnya estetik sekali!")
        insertDummyReview(db, "REV004", 2, null, "Dedi Wijaya", "D", "12 Juni 2026", 5, "Pelayanan dari PT Kereta Api Wisata sangat profesional. Listrik dan kelengkapan apar tersedia lengkap.")
        insertDummyReview(db, "REV005", 3, null, "Eka Putra", "E", "05 Juni 2026", 5, "Pencahayaan alami dari jendela besarnya juara! Bersih, rapi, cocok untuk podcast atau video produk.")
        insertDummyReview(db, "REV006", 3, null, "Fanya Olivia", "F", "07 Juni 2026", 4, "Fasilitas studio lengkap, AC dingin, dan lokasinya strategis di Jakarta Selatan.")
        insertDummyReview(db, "REV007", 4, null, "Gita Amanda", "G", "20 Juni 2026", 4, "Udaranya segar sekali dengan pemandangan gunung. Cocok untuk syuting adegan drama keluarga.")
        insertDummyReview(db, "REV008", 4, null, "Heri Prasetyo", "H", "22 Juni 2026", 5, "Villa sangat luas, kolam renang bersih, dan privasi terjaga dengan baik.")
        insertDummyReview(db, "REV009", 5, null, "Indra Kusuma", "I", "14 Juni 2026", 5, "Pemandangan sawah Bali yang magis saat matahari terbit. Izin lokal dibantu oleh pengelola.")
        insertDummyReview(db, "REV010", 5, null, "Joko Susilo", "J", "19 Juni 2026", 5, "Tempat terbuka hijau yang tenang dan sangat indah. Sangat memuaskan!")
    }

    private fun insertDummyUser(db: SQLiteDatabase, id: Int, username: String, email: String, fullName: String, password: String, phone: String) {
        val values = ContentValues().apply {
            put(COLUMN_ID, id)
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_PHONE, phone)
        }
        db.insert(TABLE_USERS, null, values)
    }

    private fun insertDummyBooking(
        db: SQLiteDatabase,
        id: String,
        userId: Int,
        propertyId: Int,
        propertyName: String,
        propertyLocation: String,
        propertyThumbnail: Int,
        startDate: Long,
        endDate: Long,
        totalPrice: Long,
        status: String,
        paymentMethod: String
    ) {
        val values = ContentValues().apply {
            put(COLUMN_BOOKING_ID, id)
            put(COLUMN_BOOKING_USER_ID, userId)
            put(COLUMN_BOOKING_PROPERTY_ID, propertyId)
            put(COLUMN_BOOKING_PROPERTY_NAME, propertyName)
            put(COLUMN_BOOKING_PROPERTY_LOCATION, propertyLocation)
            put(COLUMN_BOOKING_PROPERTY_THUMBNAIL, propertyThumbnail)
            put(COLUMN_BOOKING_START_DATE, startDate)
            put(COLUMN_BOOKING_END_DATE, endDate)
            put(COLUMN_BOOKING_TOTAL_PRICE, totalPrice)
            put(COLUMN_BOOKING_STATUS, status)
            put(COLUMN_BOOKING_PAYMENT_METHOD, paymentMethod)
        }
        db.insert(TABLE_BOOKINGS, null, values)
    }

    private fun insertDummyReview(
        db: SQLiteDatabase,
        id: String,
        propertyId: Int,
        bookingId: String?,
        userName: String,
        userAvatarText: String,
        date: String,
        rating: Int,
        comment: String
    ) {
        val values = ContentValues().apply {
            put(COLUMN_REVIEW_ID, id)
            put(COLUMN_REVIEW_PROPERTY_ID, propertyId)
            if (bookingId != null) {
                put(COLUMN_REVIEW_BOOKING_ID, bookingId)
            } else {
                putNull(COLUMN_REVIEW_BOOKING_ID)
            }
            put(COLUMN_REVIEW_USER_NAME, userName)
            put(COLUMN_REVIEW_USER_AVATAR_TEXT, userAvatarText)
            put(COLUMN_REVIEW_DATE, date)
            put(COLUMN_REVIEW_RATING, rating)
            put(COLUMN_REVIEW_COMMENT, comment)
        }
        db.insert(TABLE_REVIEWS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WISHLIST")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKINGS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_REVIEWS")
        onCreate(db)
    }

    // Function to register/insert a new user
    fun registerUser(username: String, email: String, fullName: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_PHONE, "")
        }
        val result = db.insert(TABLE_USERS, null, values)
        return result != -1L
    }

    // Get user and their password for verification
    fun getUserCredentials(identifier: String): Pair<User, String>? {
        val db = readableDatabase
        val query = """
            SELECT * FROM $TABLE_USERS 
            WHERE $COLUMN_USERNAME = ? OR $COLUMN_EMAIL = ?
        """.trimIndent()
        
        val cursor = db.rawQuery(query, arrayOf(identifier, identifier))
        var result: Pair<User, String>? = null
        
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)) ?: ""
            
            val user = User(
                id = id,
                username = username,
                email = email,
                fullName = fullName,
                profilePicture = R.drawable.ic_profile,
                phone = phone
            )
            result = Pair(user, password)
        }
        cursor.close()
        return result
    }

    // Update email
    fun updateEmail(userId: Int, newEmail: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, newEmail)
        }
        val rows = db.update(TABLE_USERS, values, "$COLUMN_ID = ?", arrayOf(userId.toString()))
        return rows > 0
    }

    // Update profile
    fun updateProfile(userId: Int, fullName: String, phone: String, email: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_PHONE, phone)
            put(COLUMN_EMAIL, email)
        }
        val rows = db.update(TABLE_USERS, values, "$COLUMN_ID = ?", arrayOf(userId.toString()))
        return rows > 0
    }

    // Update password
    fun updatePassword(userId: Int, newPassword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PASSWORD, newPassword)
        }
        val rows = db.update(TABLE_USERS, values, "$COLUMN_ID = ?", arrayOf(userId.toString()))
        return rows > 0
    }

    // Wishlist functions
    fun addToWishlist(userId: Int, propertyId: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_WISHLIST_USER_ID, userId)
            put(COLUMN_WISHLIST_PROPERTY_ID, propertyId)
        }
        val result = db.insertWithOnConflict(TABLE_WISHLIST, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        return result != -1L
    }

    fun removeFromWishlist(userId: Int, propertyId: Int): Boolean {
        val db = writableDatabase
        val rows = db.delete(TABLE_WISHLIST, "$COLUMN_WISHLIST_USER_ID = ? AND $COLUMN_WISHLIST_PROPERTY_ID = ?", arrayOf(userId.toString(), propertyId.toString()))
        return rows > 0
    }

    fun getUserWishlist(userId: Int): List<Int> {
        val db = readableDatabase
        val query = "SELECT $COLUMN_WISHLIST_PROPERTY_ID FROM $TABLE_WISHLIST WHERE $COLUMN_WISHLIST_USER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        val list = mutableListOf<Int>()
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getInt(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    // Booking functions
    fun addBooking(userId: Int, booking: Booking): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BOOKING_ID, booking.id)
            put(COLUMN_BOOKING_USER_ID, userId)
            put(COLUMN_BOOKING_PROPERTY_ID, booking.propertyId)
            put(COLUMN_BOOKING_PROPERTY_NAME, booking.propertyName)
            put(COLUMN_BOOKING_PROPERTY_LOCATION, booking.propertyLocation)
            put(COLUMN_BOOKING_PROPERTY_THUMBNAIL, booking.propertyThumbnail)
            put(COLUMN_BOOKING_START_DATE, booking.startDate)
            put(COLUMN_BOOKING_END_DATE, booking.endDate)
            put(COLUMN_BOOKING_TOTAL_PRICE, booking.totalPrice)
            put(COLUMN_BOOKING_STATUS, booking.status)
            put(COLUMN_BOOKING_PAYMENT_METHOD, booking.paymentMethod)
        }
        val result = db.insert(TABLE_BOOKINGS, null, values)
        return result != -1L
    }

    fun getUserBookings(userId: Int): List<Booking> {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_BOOKINGS WHERE $COLUMN_BOOKING_USER_ID = ? ORDER BY $COLUMN_BOOKING_START_DATE DESC"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        val list = mutableListOf<Booking>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID))
                val propertyId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_PROPERTY_ID))
                val propertyName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_PROPERTY_NAME))
                val propertyLocation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_PROPERTY_LOCATION))
                val propertyThumbnail = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_PROPERTY_THUMBNAIL))
                val startDate = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_START_DATE))
                val endDate = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_END_DATE))
                val totalPrice = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_TOTAL_PRICE))
                val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_STATUS))
                val paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_PAYMENT_METHOD))
                
                list.add(
                    Booking(
                        id = id,
                        propertyId = propertyId,
                        propertyName = propertyName,
                        propertyLocation = propertyLocation,
                        propertyThumbnail = propertyThumbnail,
                        startDate = startDate,
                        endDate = endDate,
                        totalPrice = totalPrice,
                        status = status,
                        paymentMethod = paymentMethod
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    // Review functions
    fun addReview(review: Review): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_REVIEW_ID, review.id)
            put(COLUMN_REVIEW_PROPERTY_ID, review.propertyId)
            if (review.bookingId != null) {
                put(COLUMN_REVIEW_BOOKING_ID, review.bookingId)
            } else {
                putNull(COLUMN_REVIEW_BOOKING_ID)
            }
            put(COLUMN_REVIEW_USER_NAME, review.userName)
            put(COLUMN_REVIEW_USER_AVATAR_TEXT, review.userAvatarText)
            put(COLUMN_REVIEW_DATE, review.date)
            put(COLUMN_REVIEW_RATING, review.rating)
            put(COLUMN_REVIEW_COMMENT, review.comment)
        }
        val result = db.insert(TABLE_REVIEWS, null, values)
        return result != -1L
    }

    fun getAllReviews(): List<Review> {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_REVIEWS"
        val cursor = db.rawQuery(query, null)
        val list = mutableListOf<Review>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID))
                val propertyId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_PROPERTY_ID))
                val bookingId = if (cursor.isNull(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_BOOKING_ID))) {
                    null
                } else {
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_BOOKING_ID))
                }
                val userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_USER_NAME))
                val userAvatarText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_USER_AVATAR_TEXT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_DATE))
                val rating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_RATING))
                val comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_COMMENT))

                list.add(
                    Review(
                        id = id,
                        propertyId = propertyId,
                        bookingId = bookingId,
                        userName = userName,
                        userAvatarText = userAvatarText,
                        date = date,
                        rating = rating,
                        comment = comment
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}
