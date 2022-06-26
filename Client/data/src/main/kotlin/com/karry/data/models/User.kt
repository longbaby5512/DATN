package com.karry.data.models

sealed class User {
    data class User (
        val id: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val status: Boolean? = null
    ) {
        val fullName: String
            get() = "$firstName $lastName"
    }

    data class Register(
        val email: String,
        val password: String,
        val firstName: String,
        val lastName: String
    )

    data class Login(
        val email: String,
        val password: String
    )
}
