package com.combocart.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.theme.DarkGray
import com.combocart.ui.theme.LightGray
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.combocart.ui.home.NotificationViewModel
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFBFBFB))
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF1A1A1A))
                }
                Text(
                    "Notifications", 
                    fontSize = 18.sp, 
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            }

            if (state.isLoading) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                      CircularProgressIndicator(color = PrimaryOrange)
                 }
            } else if (state.error != null) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                      Text(state.error ?: "Error fetching notifications", color = MediumGray)
                 }
            } else {
                // Mock data to match Image 2 precisely
                val notifications = if (state.notifications.isEmpty()) {
                    listOf(
                        NotificationDisplay(
                            "Order Delivered", 
                            "Your Butter Chicken Combo has been delivered", 
                            "5 mins ago", 
                            Icons.Default.Inventory2, 
                            true
                        ),
                        NotificationDisplay(
                            "Out for Delivery", 
                            "Your order is on the way", 
                            "25 mins ago", 
                            Icons.Default.LocalShipping, 
                            true
                        ),
                        NotificationDisplay(
                            "50% OFF on your next order", 
                            "Use code COMBO50 at checkout", 
                            "2 hours ago", 
                            Icons.Default.LocalOffer, 
                            false
                        ),
                        NotificationDisplay(
                            "New Badge Unlocked!", 
                            "You earned the \"Spice King\" badge", 
                            "1 day ago", 
                            Icons.Default.EmojiEvents, 
                            false
                        )
                    )
                } else {
                    state.notifications.map {
                        NotificationDisplay(
                            it.title ?: "Notification",
                            it.message ?: "",
                            formatTimeAgo(it.created_at ?: ""),
                            when(it.type ?: "") {
                                "ORDER_UPDATE" -> Icons.Default.Inventory2
                                "OFFER" -> Icons.Default.LocalOffer
                                "BADGE" -> Icons.Default.EmojiEvents
                                else -> Icons.Default.Notifications
                            },
                            !it.is_read
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(notifications) { item ->
                        NotificationCard(item)
                    }
                }
            }
        }
    }
}

data class NotificationDisplay(
    val title: String, 
    val message: String, 
    val time: String, 
    val icon: ImageVector,
    val isUnread: Boolean
)

@Composable
fun NotificationCard(item: NotificationDisplay) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box {
            // Left stripe for unread
            if (item.isUnread) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(80.dp) // Approximate height to match image look
                        .align(Alignment.CenterStart)
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                        .background(PrimaryOrange)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Icon Container
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFF1F0)), // Light orange/peach bg from Figma
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon, 
                        contentDescription = null, 
                        tint = PrimaryOrange,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = item.title, 
                            fontWeight = FontWeight.Bold, 
                            fontSize = 15.sp, 
                            color = Color(0xFF2D3436)
                        )
                        
                        if (item.isUnread) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .size(8.dp)
                                    .background(Color(0xFFFF4D4D), CircleShape) // Red dot
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    Text(
                        text = item.message, 
                        fontSize = 13.sp, 
                        color = Color(0xFF636E72),
                        lineHeight = 18.sp
                    )
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    Text(
                        text = item.time, 
                        fontSize = 11.sp, 
                        color = Color(0xFFB2BEC3)
                    )
                }
            }
        }
    }
}

/**
 * Formats an ISO timestamp to a human-readable relative time string.
 * Converts UTC timestamp to IST (Asia/Kolkata) timezone.
 */
fun formatTimeAgo(isoTimestamp: String): String {
    if (isoTimestamp.isBlank()) return ""
    
    return try {
        val istZone = ZoneId.of("Asia/Kolkata")
        val now = ZonedDateTime.now(istZone)
        
        // Parse the timestamp - handle both with and without timezone
        val parsedTime = if (isoTimestamp.contains("Z") || isoTimestamp.contains("+")) {
            ZonedDateTime.parse(isoTimestamp).withZoneSameInstant(istZone)
        } else {
            // Assume UTC if no timezone specified
            java.time.LocalDateTime.parse(isoTimestamp).atZone(ZoneId.of("UTC")).withZoneSameInstant(istZone)
        }
        
        val minutes = ChronoUnit.MINUTES.between(parsedTime, now)
        val hours = ChronoUnit.HOURS.between(parsedTime, now)
        val days = ChronoUnit.DAYS.between(parsedTime, now)
        
        when {
            minutes < 1 -> "Just now"
            minutes < 60 -> "$minutes min${if (minutes > 1) "s" else ""} ago"
            hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            days < 7 -> "$days day${if (days > 1) "s" else ""} ago"
            else -> {
                // Show formatted date for older notifications
                val formatter = DateTimeFormatter.ofPattern("dd MMM, hh:mm a")
                parsedTime.format(formatter)
            }
        }
    } catch (e: Exception) {
        // If parsing fails, return the original timestamp
        isoTimestamp
    }
}
