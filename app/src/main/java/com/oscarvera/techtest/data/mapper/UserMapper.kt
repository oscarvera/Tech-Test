package com.oscarvera.techtest.data.mapper

import com.oscarvera.techtest.data.remote.dto.UserDto
import com.oscarvera.techtest.domain.model.User

fun UserDto.toDomain(): User = User(
    id = id,
    email = email,
    username = username
)