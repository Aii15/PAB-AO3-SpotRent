package com.pab.spotrent.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pab.spotrent.R
import com.pab.spotrent.data.model.User

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "spotrent.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_FULL_NAME = "fullName"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT UNIQUE,
                $COLUMN_EMAIL TEXT UNIQUE,
                $COLUMN_FULL_NAME TEXT,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)

        // Insert default dummy users
        insertDummyUser(db, 1, "admin", "admin@spotrent.com", "Admin SpotRent", "password123")
        insertDummyUser(db, 2, "user", "user@gmail.com", "John Doe", "password123")
    }

    private fun insertDummyUser(db: SQLiteDatabase, id: Int, username: String, email: String, fullName: String, password: String) {
        val values = ContentValues().apply {
            put(COLUMN_ID, id)
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_PASSWORD, password)
        }
        db.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
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
            
            val user = User(
                id = id,
                username = username,
                email = email,
                fullName = fullName,
                profilePicture = R.drawable.ic_profile
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

    // Update password
    fun updatePassword(userId: Int, newPassword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PASSWORD, newPassword)
        }
        val rows = db.update(TABLE_USERS, values, "$COLUMN_ID = ?", arrayOf(userId.toString()))
        return rows > 0
    }
}
