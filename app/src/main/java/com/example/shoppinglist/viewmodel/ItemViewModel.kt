package com.example.shoppinglist.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.shoppinglist.model.Item
import com.example.shoppinglist.roomdb.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//AndroidViewModel ile ViewModel arasındaki fark AndroidViewModel context ile çalışır.Db kodları bizden context ister
class ItemViewModel(application: Application) : AndroidViewModel(application){

    private val db = Room.databaseBuilder(
        getApplication(),
        ItemDatabase::class.java, "Items"
    ).build()

    private val itemDao = db.itemDao()


    val itemList = mutableStateOf<List<Item>>(listOf())
    val selectedItem = mutableStateOf<Item?>(null)

    //Uygulamada kullancağımız fonksiyonları tanımlıyoruz
    fun getItems(){
        viewModelScope.launch(Dispatchers.IO) {
            itemList.value = itemDao.getItemWithNameAndId()
        }
    }

    //Tek bir ürün çekmek için
    fun getItem(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val item = itemDao.getItemById(id)
            item?.let{
                selectedItem.value = it
            }
        }
    }

    //Yeni ürün eklemek için
    fun saveItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.insertItem(item)
        }
    }

    //Ürün silmek için
    fun deleteItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.deleteItem(item)
        }
    }


}