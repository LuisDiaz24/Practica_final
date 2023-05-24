package com.example.practica_final.domain

import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
) : Serializable {
    constructor() : this(0, "", "", 0.0, 0.0, 0.0, 0, "", "", "", listOf())
}
