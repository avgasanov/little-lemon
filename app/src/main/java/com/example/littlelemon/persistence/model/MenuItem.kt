package com.example.littlelemon.persistence.model

import com.example.littlelemon.persistence.MenuItemRecord
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MenuNetworkdata(
    @SerialName("menu")
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: Double,
    @SerialName("category")
    val category: String,
    @SerialName("image")
    val image: String,
) {
    fun toMenuItemRoom() = MenuItemRecord(
        id = id,
        title = title,
        description = description,
        price = price,
        image = image,
        category = category,
    )
}
