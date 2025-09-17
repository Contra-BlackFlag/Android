package com.example.sih

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import com.example.sih.ui.theme.SIHTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sih.ui.theme.RedEmergency
import kotlinx.coroutines.delay
import androidx.compose.animation.core.*
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// --- (Placeholder for TopBar and BottomNavigationBar if we want to make them reusable across screens) ---
// For now, let's keep them embedded or copy-pasted for simplicity in this walkthrough.
// In a real app, you'd hoist them to a common location.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen(navController: NavController) {
    // Scaffold provides the basic visual structure for screens
    Scaffold(
        topBar = {
            // Top Bar for Emergency Screen
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "EMERGENCY",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black // Assuming black text on white/light background
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()  }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Handle profile action */ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White // White background for top bar
                )
            )
        },
        bottomBar = {
            // Placeholder for Bottom Navigation. In a real app, you'd use a shared composable.
            // For this screen, the SOS button would be highlighted.
        },
        containerColor = Color(0xFFF0F4F8)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .background(Color(0xFFF0F4F8)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Need Immediate Assistance?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tap to alert authorities & contacts",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(24.dp))

            EmergencyActivationCard() // CALL YOUR NEW CARD HERE!
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencyScreenPreview() {
    SIHTheme {
        EmergencyScreen(rememberNavController())
    }
}

@Composable
fun EmergencyActivationCard() {
    // State for managing press, release, and progress
    var isPressing by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }

    // Get the haptic feedback interface
    val haptic = LocalHapticFeedback.current

    // Pulsing animation for when the button is idle
    val infiniteTransition = rememberInfiniteTransition(label = "pulseTransition")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "pulseScale"
    )

    // This effect runs the 3-second timer when the button is being pressed
    LaunchedEffect(isPressing) {
        if (isPressing) {
            val startTime = System.currentTimeMillis()
            val duration = 3000L // 3 seconds hold time
            while (isPressing && (System.currentTimeMillis() - startTime < duration)) {
                val elapsedTime = System.currentTimeMillis() - startTime
                progress = (elapsedTime.toFloat() / duration).coerceIn(0f, 1f)
                delay(16) // Update roughly 60 times per second
            }
            // If still pressing after 3 seconds, activate SOS
            if (isPressing && progress >= 1f) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress) // Signal activation
                println("SOS ACTIVATED!") // Placeholder for actual activation
                isPressing = false // Reset state after activation
            }
        } else {
            progress = 0f // Reset progress if released early
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .graphicsLayer(scaleX = if (isPressing) 1f else pulseScale, scaleY = if (isPressing) 1f else pulseScale)
                    .clip(CircleShape)
                    .background(RedEmergency)
                    // This is the corrected input handler
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPressing = true // Set state to true when finger touches down
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress) // Vibrate on initial press
                                try {
                                    awaitRelease() // Wait until the finger is lifted
                                } finally {
                                    isPressing = false // Set state to false when finger is lifted
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                // Visual progress indicator shown while pressing
                if (isPressing && progress < 1f) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxSize(),
                        color = Color.White.copy(alpha = 0.5f),
                        strokeWidth = 8.dp,
                        trackColor = Color.Transparent
                    )
                }

                // Text inside the button
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "PRESS & HOLD",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if (isPressing) "ACTIVATING..." else "FOR 3 SEC",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Current Location Display (placeholder data)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Map, contentDescription = "Location", tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "CURRENT LOCATION:", color = Color.DarkGray, fontSize = 13.sp)
                    Text(text = "Lat: 34.9972, Lon: 135.766", color = Color.Black, fontWeight = FontWeight.Medium)
                    Text(text = "Address: 123 Main St, Kyoto, Japan", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sharing options
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Share Digital ID with responders", color = Color.Black, modifier = Modifier.weight(1f))
                Switch(
                    checked = true,
                    onCheckedChange = { /* TODO */ },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = RedEmergency,
                        checkedTrackColor = RedEmergency.copy(alpha = 0.5f)
                    )
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Notify Emergency Contacts", color = Color.Black, modifier = Modifier.weight(1f))
                Switch(
                    checked = true,
                    onCheckedChange = { /* TODO */ },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = RedEmergency,
                        checkedTrackColor = RedEmergency.copy(alpha = 0.5f)
                    )
                )
            }
        }
    }
}