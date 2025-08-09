package com.example.shoppinglist.screens

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.shoppinglist.model.Item
import com.example.shoppinglist.R
import java.io.ByteArrayOutputStream


@Composable
fun AddItemScreen(saveFunction: (Item) -> Unit) {

    val itemName = remember { mutableStateOf("") }
    val storeName = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }

    // Arkaplan rengini buradan alalım ki TextField içindeki metin görünsün
    val screenBackgroundColor = MaterialTheme.colorScheme.inversePrimary

    // Transparan TextField'lar için metin rengi (arkaplana göre seçilmeli)
    val textFieldTextColor = MaterialTheme.colorScheme.onSurface

    val context = LocalContext.current

    var selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = screenBackgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            ImagePicker(onImageSelected = { uri ->
                selectedImageUri.value = uri
            })


            // Transparan TextField
            TextField(
                value = itemName.value,
                onValueChange = { itemName.value = it },
                placeholder = { Text("Item Name", color = textFieldTextColor.copy(alpha = 0.6f)) },
                textStyle = TextStyle(color = textFieldTextColor),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = textFieldTextColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = storeName.value,
                onValueChange = { storeName.value = it },
                placeholder = { Text("Store Name", color = textFieldTextColor.copy(alpha = 0.6f)) },
                textStyle = TextStyle(color = textFieldTextColor),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = textFieldTextColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = price.value,
                onValueChange = { price.value = it },
                placeholder = { Text("Price", color = textFieldTextColor.copy(alpha = 0.6f)) },
                textStyle = TextStyle(color = textFieldTextColor),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = textFieldTextColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {

                val imageByteArray = selectedImageUri.value?.let {
                    resizeImage(context, it as Uri,600,400)
                } ?: ByteArray(0)

                val itemToInsert = Item(
                    itemName.value,
                    storeName.value,
                    price.value,
                    imageByteArray
                )
                saveFunction(itemToInsert)
            }) {
                Text("Save")
            }
        }
    }
}


@Composable
fun ImagePicker(onImageSelected : (Uri?) -> Unit){

    //Uri seçeceğimiz fotonun hangi yolda klasörde olduğunu bulur
    var selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val permission  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    //fotoğrafın seçildiği yolu alma
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri.value = uri
    }

    //izin verildi verilmedi kontrolü
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->
        if (granted){
            //doğrudan androidin resimleri gösteren klasörüne gider
            galleryLauncher.launch("image/*")
        }else{
            Toast.makeText(context,"Permission denied!", Toast.LENGTH_LONG).show()
        }
    }

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        selectedImageUri.value?.let {
            Image(painter = rememberAsyncImagePainter(model = it),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(300.dp,200.dp)
                    .padding(16.dp))
            onImageSelected(it)
        } ?: Image(painter = painterResource(R.drawable.ic_launcher_foreground),
            "Select Image",
            modifier = Modifier
                .size(300.dp,200.dp)
                .fillMaxWidth()
                .padding(16.dp)
                .clickable{
                    if (ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED){
                        galleryLauncher.launch("image/*")
                    }else{
                        permissionLauncher.launch(permission)
                    }
                })

    }


}

fun resizeImage(context: Context,uri: Uri,maxWidth: Int, maxHeight: Int): ByteArray? {

    return try{
        val inputStream = context.contentResolver.openInputStream(uri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        val ratio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()

        var width = maxWidth
        var height = (width/ratio).toInt()

        if (height>maxHeight){
            height=maxHeight
            width = (height*ratio).toInt()
        }

        val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap,width,height,true)

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
        byteArrayOutputStream.toByteArray()
    }catch (e: Exception){
        e.printStackTrace()
        null
    }

}
