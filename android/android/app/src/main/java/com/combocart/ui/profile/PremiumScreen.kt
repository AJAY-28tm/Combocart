package com.combocart.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Combo Gold Premium", color = White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Black) // Premium feel
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                Brush.verticalGradient(listOf(Black, Color(0xFF424242))),
                                RoundedCornerShape(24.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Combo Gold", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = YellowRating)
                            Text("Unlock Premium Benefits", color = White)
                        }
                    }
                }

                // Why Gold?
                item {
                    Text("Why Go Gold?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                val benefits = listOf(
                    "Free Delivery" to "unlimited free delivery on all orders above ₹199",
                    "Extra 10% Off" to "additional discount on all meal kits",
                    "Priority Support" to "24/7 dedicated customer success partner",
                    "Early Access" to "get new recipes 24 hours before everyone else"
                )

                items(benefits.size) { index ->
                    val (title, desc) = benefits[index]
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(desc, fontSize = 12.sp, color = MediumGray)
                        }
                    }
                }

                // Offer Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
                    ) {
                        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("SPECIAL OFFER", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryOrange)
                            Text("3 Months FREE", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text("then ₹499/year. Cancel anytime.", fontSize = 14.sp, color = MediumGray)
                        }
                    }
                }
            }

            // Bottom Button
            Button(
                onClick = { 
                    // Simulate payment then navigate back
                    navController.navigate(Screen.Payment.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Black),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Start 3-Month Free Trial", color = YellowRating, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}
