package com.example.shoppinglist

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.todo.LocationUtil
import com.example.todo.LocationViewModel
import com.example.todo.MainActivity
import kotlinx.serialization.internal.throwMissingFieldException

data class ShoppingItem(val id:Int,
                        var name:String,
                        var quantity:Int,
                        var IsEditing : Boolean = false,
                        var address : String = ""){

}
@Composable
fun ShoppingList(
    locationUtils : LocationUtil,
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    address: String
){
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var ShowDialog by remember { mutableStateOf(false) }
    var Name by remember { mutableStateOf("") }
    var IQuantity by remember { mutableStateOf("") }

    val requestPermissionLauncher = rememberLauncherForActivityResult( contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION]  == true && permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true){


            }
            else{
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                if (rationaleRequired){
                    Toast.makeText(context,
                        "Location Needed",
                        Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(context,
                        "Setting->More Networks->Location",
                        Toast.LENGTH_LONG).show()
                }
            }
        })


    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

        Button(onClick = { ShowDialog = true}, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(40.dp)) {
            Text(text = "Add Item")
        }

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            items(sItems){
                item->
                if (item.IsEditing){
                    ShopEditor(item = item, onEditComplete = {
                       editedname, editedquantity ->
                        sItems = sItems.map { it.copy(IsEditing = false) }
                        val editedItem = sItems.find { it.id == item.id }
                        editedItem?.let {
                            it.name = editedname
                            it.quantity = editedquantity
                        }
                    } )
                }
                else{
                    ShoppingItemList(item = item, onEditClick = {
                        sItems = sItems.map { it.copy(IsEditing = it.id == item.id) }
                    }, onDeleteClick = {sItems = sItems - item})
                }
            }

        }
    }
    if (ShowDialog){
        AlertDialog(onDismissRequest = { ShowDialog = false }, confirmButton = {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    if (Name.isNotBlank()){
                        val newItem = ShoppingItem(id = sItems.size + 1, name = Name, quantity = IQuantity.toInt())
                        sItems = sItems + newItem
                        ShowDialog = false
                        Name = ""
                        IQuantity = ""
                    }

                }) {
                    Text("Add")
                }
                Button(onClick = {ShowDialog = false}) {
                    Text("Cancel")
                }

            }
        },
            title = { Text("Add Shopping Item")},
            text = {
                Column {
                    OutlinedTextField(value = Name, onValueChange = {Name = it} ,
                        singleLine = true, modifier = Modifier.fillMaxWidth().padding(8.dp))
                    OutlinedTextField(value = IQuantity, onValueChange = {IQuantity = it},
                        singleLine = true, modifier = Modifier.fillMaxWidth().padding(8.dp))
                }
            })


    }
}
@Composable
fun ShopEditor(item: ShoppingItem,onEditComplete : (String,Int) -> Unit){
    var editedname by remember { mutableStateOf(item.name) }
    var editedquantity by remember { mutableStateOf(item.quantity.toString()) }
    var Editing by remember { mutableStateOf(item.IsEditing) }
    Row (modifier = Modifier.fillMaxWidth().background(Color.White).padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly){
        Column() {
            BasicTextField(value = editedname, onValueChange = {editedname = it}, singleLine = true
                , modifier = Modifier.wrapContentSize().padding(8.dp))
            BasicTextField(value = editedquantity, onValueChange = {editedquantity = it}, singleLine = true
                , modifier = Modifier.wrapContentSize().padding(8.dp))
        }
        Button(
            onClick = {
                Editing = false
                onEditComplete(editedname,editedquantity.toIntOrNull() ?: 1)
            }
        ) {
            Text("Save")
        }
    }

}

@Composable
fun ShoppingItemList(item: ShoppingItem,onEditClick : ()->Unit,onDeleteClick : ()->Unit){
    Row(
        modifier = Modifier.padding(8.dp).fillMaxWidth().border(border = BorderStroke(4.dp, Color.Black), shape  = RoundedCornerShape(30) )
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(8.dp)
        ) {
            Row {
                Text(text = item.name, modifier = Modifier.padding(8.dp))
                Text(text = "Qty : ${item.quantity}", modifier = Modifier.padding(8.dp))
            }
            Row(Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                Text(text = item.address)
            }
        }
        Row(modifier = Modifier.padding(8.dp)) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Button")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Button")
            }
        }
    }
}
