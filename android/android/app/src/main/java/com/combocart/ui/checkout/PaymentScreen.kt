package com.combocart.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.GreenSuccess
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import com.combocart.ui.cart.CartViewModel

@Composable
fun PaymentScreen(
    navController: NavController,
    cartViewModel: CartViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    checkoutViewModel: CheckoutViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val cartState by cartViewModel.state.collectAsState()
    val checkoutState by checkoutViewModel.state.collectAsState()
    
    val total = cartState.cart?.total_price ?: 0.0

    // Handle navigation when payment is successful
    LaunchedEffect(checkoutState.paymentResponse) {
        if (checkoutState.paymentResponse != null) {
            navController.navigate(Screen.OrderConfirmed.route) {
                popUpTo(Screen.Cart.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // Header
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Column {
                Text("Payment", fontSize = 16.sp)
                Text("₹$total", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        if (checkoutState.isLoading) {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                  androidx.compose.material3.CircularProgressIndicator(color = PrimaryOrange)
             }
        } else {
             Column(modifier = Modifier.padding(16.dp)) {
                if (checkoutState.error != null) {
                    Text("Error: ${checkoutState.error}", color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
                }
                
                Text("Recommended Options", fontWeight = FontWeight.Bold, color = MediumGray)
                Spacer(modifier = Modifier.height(16.dp))
                
                PaymentOptionItem("UPI / Google Pay", "Auto-detects UPI apps for quick payments")
                PaymentOptionItem("Axis Bank •••• 1234", "Debit Card, expires 12/25")
    
                Spacer(modifier = Modifier.height(32.dp))
                
                Text("Other Payment Methods", fontWeight = FontWeight.Bold, color = MediumGray)
                Spacer(modifier = Modifier.height(16.dp))
                
                PaymentOptionItem("Credit / Debit Card", "Visa, Mastercard, Amex & more")
                PaymentOptionItem("Wallets", "Paytm, PhonePe, OlaMoney")
                PaymentOptionItem("Netbanking", "All major banks supported")
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Lock, null, tint = GreenSuccess, modifier = Modifier.size(16.dp))
                Text(" Secure Payments Guaranteed", color = GreenSuccess, fontSize = 12.sp)
            }
            
            Button(
                onClick = { checkoutViewModel.placeOrder() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp),
                enabled = !checkoutState.isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Pay Securely ₹$total", color = White)
            }
        }
    }
}

@Composable
fun PaymentOptionItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(40.dp).background(Color.LightGray, RoundedCornerShape(4.dp))) // Icon placeholder
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Text(subtitle, fontSize = 12.sp, color = MediumGray)
        }
        Icon(Icons.Default.KeyboardArrowRight, null, tint = MediumGray)
    }
}
