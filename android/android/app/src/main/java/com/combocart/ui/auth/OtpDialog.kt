package com.combocart.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.MediumGray

@Composable
fun OtpDialog(
    phoneNumber: String,
    generatedOtp: String,
    onDismiss: () -> Unit,
    onVerify: (String) -> Unit
) {
    var otpValue by remember { mutableStateOf("") }
    val context = androidx.compose.ui.platform.LocalContext.current
    
    androidx.compose.runtime.DisposableEffect(Unit) {
        val receiver = com.combocart.common.SmsReceiver()
        receiver.setOnSmsReceivedListener { otp ->
            otpValue = otp
            // Optional: Auto-verify if OTP is received
            // onVerify(otp) 
        }
        
        val filter = android.content.IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        context.registerReceiver(receiver, filter)
        
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Verify OTP",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    "Enter the 4-digit code sent to $phoneNumber",
                    fontSize = 14.sp,
                    color = MediumGray,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Show demo OTP
                Text(
                    "(Demo OTP: $generatedOtp)",
                    fontSize = 14.sp,
                    color = PrimaryOrange,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // OTP Input Field
                OutlinedTextField(
                    value = otpValue,
                    onValueChange = { 
                        if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                            otpValue = it
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("0 0 0 0", textAlign = TextAlign.Center) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrange,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        letterSpacing = 8.sp
                    )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MediumGray
                        )
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = { onVerify(otpValue) },
                        modifier = Modifier.weight(1f),
                        enabled = otpValue.length == 4,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryOrange,
                            disabledContainerColor = PrimaryOrange.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Verify & Continue")
                    }
                }
            }
        }
    }
}
