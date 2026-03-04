package com.oscarvera.techtest.data.mapper

import com.oscarvera.techtest.data.local.entity.FavoriteEntity
import com.oscarvera.techtest.domain.model.Product

fun Product.toFavoriteEntity(): FavoriteEntity = FavoriteEntity(
    productId = id,
    title = title,
    image = image,
    price = price
)

fun FavoriteEntity.toDomainProduct(): Product = Product(
    id = productId,
    title = title,
    price = price,
    description = "",
    category = "",
    image = image
)