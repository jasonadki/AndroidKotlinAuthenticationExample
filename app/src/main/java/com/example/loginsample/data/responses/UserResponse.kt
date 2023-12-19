package com.example.loginsample.data.responses

data class UserResponse(
    val created_at: String,
    val email: String,
    val first_name: String?,
    val id: Int,
    val is_active: Boolean,
    val last_name: String?,
    val phone_number: Any?,
    val profile_picture: Any?
)