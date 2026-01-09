package com.combocart.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

@Composable
fun ManageAddressScreen(navController: NavController) {
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
                Text("Manage Addresses", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    AddressItem("Home", "E204, Sunrise Apartments, MG Road, Koramangala, Bangalore - 560034", true)
                }
                item {
                    AddressItem("Work", "Tech Park, 4th Floor, Indiranagar, Bangalore - 560038", false)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { /* Navigate to Add Address */ },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = PrimaryOrange),
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryOrange),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add New Address")
            }
        }
    }
}

@Composable
fun AddressItem(title: String, address: String, isDefault: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, if (isDefault) PrimaryOrange else Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                 Icon(Icons.Default.Home, null, tint = if (isDefault) PrimaryOrange else MediumGray)
                 Spacer(modifier = Modifier.width(8.dp))
                 Text(title, fontWeight = FontWeight.Bold)
            }
            if (isDefault) {
                Text("DEFAULT", fontSize = 10.sp, color = PrimaryOrange, fontWeight = FontWeight.Bold, modifier = Modifier.background(PrimaryOrange.copy(alpha=0.1f), RoundedCornerShape(4.dp)).padding(4.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(address, color = MediumGray, fontSize = 14.sp)
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Text("Edit", color = PrimaryOrange, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(end = 16.dp))
            Text("Delete", color = Color.Red, fontWeight = FontWeight.SemiBold)
        }
    }
}
