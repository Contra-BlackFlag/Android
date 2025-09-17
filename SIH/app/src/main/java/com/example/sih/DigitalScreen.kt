package com.example.safewayapp.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sih.ui.theme.SIHTheme
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

// --- This is the new, reliable QR Code generator function using ZXing ---
@Composable
fun rememberQrBitmap(content: String, size: Int = 800): Bitmap? {
    return remember(content) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, size, size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalIdScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MY DIGITAL ID", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Handle settings action */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF0F4F8)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Your Secure Digital Identity", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Powered by Blockchain", color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp))

            DigitalIdCard() // Call the main card

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Text("View Full Details")
            }
            OutlinedButton(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Text("Share Temporarily")
            }
        }
    }
}

@Composable
fun IdDetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.width(120.dp)
        )
        Text(text = value, fontWeight = FontWeight.Medium, color = Color.Black)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun DigitalIdCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // User Info Section
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(80.dp).clip(CircleShape).background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("SC", color = Color.White, fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Piyush the Goat", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Blockchain Verified", color = Color(0xFF4CAF50), fontWeight = FontWeight.Medium)
                }
            }
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Details Section
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                IdDetailRow(label = "Nationality", value = "Canada")
                IdDetailRow(label = "Date of Birth", value = "1995-08-16")
                IdDetailRow(label = "Passport No.", value = "CA...XXXXX345")
                IdDetailRow(label = "Unique ID", value = "0x...XXXXX7A2")
            }
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // --- QR Code Section (Using ZXing) ---
            val qrBitmap = rememberQrBitmap(content = "safeway-user-id-sarah-chen-unique-hash-goes-here")
            if (qrBitmap != null) {
                Image(
                    bitmap = qrBitmap.asImageBitmap(),
                    contentDescription = "Digital ID QR Code",
                    modifier = Modifier.size(180.dp).background(Color.White).padding(4.dp)
                )
            } else {
                // Fallback in case QR generation fails
                Box(modifier = Modifier.size(180.dp).background(Color.LightGray), contentAlignment = Alignment.Center) {
                    Text("QR Error", color = Color.Black)
                }
            }
            Text("Scan to Verify Authenticity", color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DigitalIdScreenPreview() {
    SIHTheme {
        DigitalIdScreen(rememberNavController())
    }
}