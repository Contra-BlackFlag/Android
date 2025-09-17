package com.example.sih

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Call // For Emergency
import androidx.compose.material.icons.filled.QrCode // For Digital ID (or choose another like VpnKey)
import androidx.compose.material.icons.filled.Map // For Safety Zones
import androidx.compose.material.icons.filled.Campaign // For Incident Report (or choose another like ReportProblem)
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector // For passing icon as a parameter
import androidx.compose.foundation.shape.RoundedCornerShape // For button shapes
import androidx.compose.material3.Button // For our action buttons
import androidx.compose.material3.ButtonDefaults // For button colors
import androidx.compose.ui.text.style.TextAlign // For centering text in buttons
import com.example.sih.ui.theme.BlueMap
import com.example.sih.ui.theme.GreenSafety
import com.example.sih.ui.theme.OrangeReport
import com.example.sih.ui.theme.RedEmergency
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold // For the main screen layout structure
import androidx.compose.material3.BottomAppBar // For the bottom navigation bar
import androidx.compose.material3.NavigationBarItem // For individual navigation items
import androidx.compose.material3.NavigationBarItemDefaults // For styling navigation items
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.ShieldMoon // Using this for SOS
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// (You might need to adjust or add these based on your icon choices)
@Composable
fun HomeScreen(navController: NavController) {

    // Scaffold provides slots for topBar, bottomBar, etc.
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // Pass the current route to highlight
        },
        containerColor = Color(0xFFF0F4F8) // Set overall background color for the Scaffold
    ) { paddingValues -> // This padding is important!
        // All your existing content goes into the content slot of Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                // Use paddingValues from Scaffold to ensure content isn't hidden by bottom bar
                .padding(paddingValues)
                .padding(horizontal = 16.dp) // Add horizontal padding for consistency
        ) {
            TopBar(navController = navController)
            SafetyStatusCard()
            Spacer(modifier = Modifier.height(16.dp))
            QuickActionsCard(navController)
            // If you have more content, it would go here and potentially become scrollable
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
   HomeScreen(rememberNavController())
}


@Composable
fun TopBar(navController: NavController) { // Update TopBar to accept navController
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigate(Route.ALERTS) }) { // Example nav to Alerts
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon")
        }
        Text(
            text = "SAFEWAY",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { navController.navigate(Route.DIGITAL_ID) }) { // Example nav to Digital ID
            Icon(imageVector = Icons.Default.Person, contentDescription = "Profile Icon")
        }
    }
    // Add some vertical space between the TopBar and the welcome message
    Spacer(modifier = Modifier.height(24.dp))

    // Welcome message text
    Text(text = "Welcome, Piyush The Goat", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Text(text = "Currently in Kyoto, Japan", color = Color.Gray)

    // Add space before the next card
    Spacer(modifier = Modifier.height(16.dp))
}



@Composable
fun SafetyStatusCard() {
    // Card is a surface with elevation and rounded corners.
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large, // Use the theme's defined large shape
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A4B60)) // Dark blue-grey
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Safety Status Icon",
                tint = Color(0xFF4CAF50), // A nice green color
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "All Clear - Enjoy Your Trip!",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Monitoring active zones",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .widthIn(min = 150.dp, max = 170.dp) // Constrain width for a grid-like appearance
            .height(90.dp).padding(2.dp), // Fixed height for consistency
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp), // Slightly rounded corners
        contentPadding = PaddingValues(8.dp) // Padding inside the button
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, fontSize = 12.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun QuickActionsCard(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // White background for the card
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // First Row of buttons
            Row( /* ... */ ) {
                ActionButton(
                    icon = Icons.Default.Call,
                    text = "Emergency",
                    backgroundColor = RedEmergency,
                    contentColor = Color.White,
                    onClick = { navController.navigate(Route.EMERGENCY) } // Navigate to Emergency
                )
                ActionButton(
                    icon = Icons.Default.QrCode,
                    text = "My Digital ID",
                    backgroundColor = GreenSafety,
                    contentColor = Color.White,
                    onClick = { navController.navigate(Route.DIGITAL_ID) } // Navigate to Digital ID
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Second Row of buttons
            Row( /* ... */ ) {
                ActionButton(
                    icon = Icons.Default.Map,
                    text = "Safety Zones",
                    backgroundColor = BlueMap,
                    contentColor = Color.White,
                    onClick = { navController.navigate(Route.SAFETY_ZONES_MAP) } // Navigate to Safety Zones Map
                )
                ActionButton(
                    icon = Icons.Default.Campaign,
                    text = "Report Incident",
                    backgroundColor = OrangeReport,
                    contentColor = Color.White,
                    onClick = { /* TODO: Navigate to Incident Report screen (future) */ }
                )
            }
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavController) { // Update to accept NavController
    val navItems = listOf(
        NavItem(Icons.Default.Home, "Home", Route.HOME),
        NavItem(Icons.Default.Map, "Map", Route.SAFETY_ZONES_MAP), // Changed to SAFETY_ZONES_MAP
        NavItem(Icons.Default.ShieldMoon, "SOS", Route.EMERGENCY), // Changed to EMERGENCY
        NavItem(Icons.Default.QrCode, "ID", Route.DIGITAL_ID),    // Changed to DIGITAL_ID
        NavItem(Icons.Default.Notifications, "Alerts", Route.ALERTS)
    )

    // Get the current route from the navigation back stack
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFF3A4B60), // Dark blue-grey background
        contentColor = Color.White,
        tonalElevation = 4.dp // A slight shadow effect
    ) {
        navItems.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) { // Avoid re-navigating to current screen
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting previously selected item
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (item.route == "sos") RedEmergency else if (isSelected) Color.White else Color.LightGray,
                        modifier = Modifier.size(if (item.route == "sos") 36.dp else 24.dp) // Larger SOS icon
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (item.route == "sos") RedEmergency else if (isSelected) Color.White else Color.LightGray,
                        fontSize = if (item.route == "sos") 10.sp else 12.sp,
                        fontWeight = if (item.route == "sos") FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent, // No default indicator for selected item
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray
                )
            )
        }
    }
}