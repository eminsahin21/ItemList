package com.example.shoppinglist.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.model.Item


@Composable
fun DetailScreen(item:Item, deleteFunction: () -> Unit){

    Box(modifier = Modifier.fillMaxSize()
        .background(color = MaterialTheme.colorScheme.inversePrimary)
        .padding(5.dp), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally ) {

            val imageBitmap = item.image?.let { byteArray ->
                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()

            }
            Image(bitmap = imageBitmap ?: ImageBitmap.imageResource(id=R.drawable.ic_launcher_foreground),
                contentDescription = item.itemName,
                modifier = Modifier.padding(10.dp)
                    .size(300.dp,200.dp))


            Text(text = item.itemName,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(8.dp))


            Text(text = item.price ?:"",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center)


            Spacer(modifier = Modifier.height(8.dp))

            Text(text = item.storeName ?: "",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                deleteFunction()
            }) {
                Text("Delete")
            }

        }
    }


}
