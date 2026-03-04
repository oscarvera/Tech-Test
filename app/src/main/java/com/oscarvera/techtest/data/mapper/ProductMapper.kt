package com.oscarvera.techtest.data.mapper

import com.oscarvera.techtest.data.remote.dto.ProductDto
import com.oscarvera.techtest.domain.model.Product

fun ProductDto.toDomain(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image
)