package com.example.musicapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SubscriptionView(){
    Column(modifier = Modifier.height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Manage Subscription")
        Card (modifier = Modifier.padding(8.dp),
            elevation = 10.dp){
            Column(modifier = Modifier.padding(8.dp)) {
              Column {
                  Text("Musical")
                  Row(modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween) {
                      Text("Free Tier")
                      TextButton(onClick = {}) {
                          Row {
                              Text("See all plans")
                              Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                                  contentDescription = "See All plans")

                          }
                      }
                  }
              }
                Divider(thickness = 3.dp,
                    modifier = Modifier.padding(horizontal = 8.dp))
                Row (Modifier.padding(vertical = 16.dp)){
                    Icon(imageVector = Icons.Default.AccountBox,
                        contentDescription = "")
                    Spacer(Modifier.padding(5.dp))
                    Text("Get a Plan")
                }


            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SubscriptionViewPreview(){
    SubscriptionView()
}