package com.example.shoppinglist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Item(
    @ColumnInfo(name = "item_name")
    var itemName: String,

    @ColumnInfo(name = "store_name")
    var storeName: String?,

    @ColumnInfo(name = "price")
    var price: String?,

    @ColumnInfo(name = "image")
    var image : ByteArray? = null,
    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
