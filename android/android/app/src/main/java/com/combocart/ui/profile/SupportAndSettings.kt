package com.combocart.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.theme.*

@Composable
fun SupportScreen(navController: NavController) {
    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Support & FAQ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Frequently Asked Questions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            FAQItem("Where is my order?", "You can track your active order status in the Orders tab.")
            FAQItem("How do I cancel?", "Orders can be canceled within 2 minutes of placing them.")
            FAQItem("Contact Customer Care", "Reach us at support@combocart.com or call 1800-123-4567")
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }
    
    Column(modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }.padding(vertical = 12.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(question, fontWeight = FontWeight.SemiBold)
            Icon(if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, null, tint = MediumGray)
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(answer, color = MediumGray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color(0xFFEEEEEE))
    }
}

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val notifEnabled by viewModel.notificationsEnabled.collectAsState()
    val locationEnabled by viewModel.locationEnabled.collectAsState()

    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Settings", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            SettingsToggle("Push Notifications", notifEnabled) { viewModel.toggleNotifications(it) }
            SettingsToggle("Location Access", locationEnabled) { viewModel.toggleLocation(it) }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("About App", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(vertical=8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                 Text("Version")
                 Text("1.0.0", color = MediumGray)
            }
            Row(modifier = Modifier.fillMaxWidth().padding(vertical=8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                 Text("Terms of Service")
                 Icon(Icons.Default.KeyboardArrowRight, null, tint = MediumGray)
            }
             Row(modifier = Modifier.fillMaxWidth().padding(vertical=8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                 Text("Privacy Policy")
                 Icon(Icons.Default.KeyboardArrowRight, null, tint = MediumGray)
            }
        }
    }
}

@Composable
fun SettingsToggle(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = White, checkedTrackColor = PrimaryOrange)
        )
    }
}
