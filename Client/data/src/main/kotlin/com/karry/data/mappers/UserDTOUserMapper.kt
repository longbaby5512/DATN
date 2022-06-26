package com.karry.data.mappers

import com.karry.data.dto.account.UserDTO
import com.karry.data.models.User

fun UserDTO.toUser(): User.User {
    return User.User(id, email, firstName, lastName, status)
}