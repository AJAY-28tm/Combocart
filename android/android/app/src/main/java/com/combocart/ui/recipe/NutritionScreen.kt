package com.combocart.ui.recipe

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

@Composable
fun NutritionScreen(navController: NavController) {
    Scaffold(
        bottomBar = { com.combocart.ui.components.ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF1A1A1A))
                }
                Text("Nutrition Info", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
            }

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Paneer Tickka Masala", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1A1A1A))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Per serving nutritional information", fontSize = 14.sp, color = Color(0xFF7F8C8D))

                Spacer(modifier = Modifier.height(32.dp))

                // Top Image Placeholder (Blank search-like box in Image 3)
                Card(
                    modifier = Modifier.fillMaxWidth().height(140.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize())
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Macronutrient Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Macronutrient Breakdown", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color(0xFF1A1A1A))
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        // Donut Chart Mock (Matching Image 3)
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(160.dp)) {
                             Canvas(modifier = Modifier.size(140.dp)) {
                                 val strokeWidth = 14.dp.toPx()
                                 drawArc(
                                     color = Color(0xFF4285F4), // Blue
                                     startAngle = -20f,
                                     sweepAngle = 100f,
                                     useCenter = false,
                                     style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                 )
                                 drawArc(
                                     color = Color(0xFF34A853), // Green
                                     startAngle = 90f,
                                     sweepAngle = 80f,
                                     useCenter = false,
                                     style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                 )
                                 drawArc(
                                     color = Color(0xFFFBBC05), // Yellow
                                     startAngle = 180f,
                                     sweepAngle = 120f,
                                     useCenter = false,
                                     style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                 )
                             }
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            LegendRow("Protein", "45 g", Color(0xFF4285F4))
                            LegendRow("Carbs", "35 g", Color(0xFF34A853))
                            LegendRow("Fats", "38 g", Color(0xFFFBBC05))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Health Benefits Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Health Benefits", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color(0xFF1A1A1A))
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        BenefitItemRow("High in protein for muscle building")
                        BenefitItemRow("Balanced macronutrients for sustained energy")
                        BenefitItemRow("Rich in essential vitamins and minerals")
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun LegendRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(16.dp).background(color, CircleShape))
            Spacer(modifier = Modifier.width(12.dp))
            Text(label, fontSize = 14.sp, color = Black, fontWeight = FontWeight.Medium)
        }
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Black)
    }
}

@Composable
fun BenefitItemRow(text: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Check, 
            contentDescription = null, 
            tint = Color(0xFF34A853), 
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, fontSize = 13.sp, color = MediumGray)
    }
}
