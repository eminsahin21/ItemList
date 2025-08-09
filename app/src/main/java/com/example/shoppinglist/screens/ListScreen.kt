package com.example.shoppinglist.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shoppinglist.model.Item

@Composable
fun ItemList(itemList: List<Item>,navController: NavController){

    Scaffold(topBar = {},
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FAB {
                navController.navigate("add_item_screen")
            }
        } , content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {
                items(itemList) {
                    ItemRow(it,navController)
                }
            }
        })


}

@Composable
fun ItemRow(item: Item,navController: NavController) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.inverseSurface)
        .clickable{
            //navigation to detailscreen
            navController.navigate(
                "detail_screen/${item.id}"
            )
        }) {

        Text(text = item.itemName,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(2.dp))
        }
}


@Composable
fun FAB(onClick: () -> Unit) {

    FloatingActionButton(onClick) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }

}