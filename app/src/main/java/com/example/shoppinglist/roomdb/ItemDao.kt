package com.example.shoppinglist.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppinglist.model.Item

@Dao
interface ItemDao {

    //İlk 2 sorgunun yapısı gereği coroutines ile çalışır (select sorgusu)  ama en alttakileri suspend olmalıdır
    @Query("SELECT item_name,id FROM item")
    fun getItemWithNameAndId() : List<Item>

    @Query("SELECT * FROM item WHERE id=:id")
    fun getItemById(id: Int) : Item?

    @Insert
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
}