package com.combocart.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.GreenSuccess
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

@Composable
fun WalletScreen(navController: NavController) {
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
                Text("Combo Wallet", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Balance Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryOrange) // Gradient preferred
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Total Balance", color = White.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("₹ 50.00", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = White)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { /* Add Money */ },
                        colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = PrimaryOrange),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Add, null)
                        Text("Add Money")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text("Recent Transactions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            TransactionItem("Added to Wallet", "+₹500", true, "Oct 30, 2:30 PM")
            TransactionItem("Order #12345", "-₹310", false, "Oct 28, 8:15 PM")
            TransactionItem("Refund processed", "+₹50", true, "Oct 25, 10:00 AM")
        }
    }
}

@Composable
fun TransactionItem(title: String, amount: String, isCredit: Boolean, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(title, fontWeight = FontWeight.SemiBold)
            Text(date, fontSize = 12.sp, color = MediumGray)
        }
        Text(amount, color = if (isCredit) GreenSuccess else Black, fontWeight = FontWeight.Bold)
    }
}
