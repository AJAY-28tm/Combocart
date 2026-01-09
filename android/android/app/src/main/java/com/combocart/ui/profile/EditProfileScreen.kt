package com.combocart.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.combocart.ui.profile.ProfileViewModel

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    var name by remember(state.user) { mutableStateOf(state.user?.full_name ?: "") }
    var email by remember(state.user) { mutableStateOf(state.user?.email ?: "") }
    var phone by remember(state.user) { mutableStateOf(state.user?.phone ?: "") }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            Toast.makeText(context, event, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(state.isUpdateSuccess) {
        if (state.isUpdateSuccess) {
            viewModel.resetUpdateStatus()
            navController.popBackStack()
        }
    }

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
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Edit Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Pic
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFEEEEEE), CircleShape)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                 Icon(Icons.Default.Person, null, tint = MediumGray, modifier = Modifier.size(60.dp))
                 // Edit Icon Overlay
                 Box(
                     modifier = Modifier
                         .align(Alignment.BottomEnd)
                         .background(PrimaryOrange, CircleShape)
                         .padding(6.dp)
                 ) {
                     Icon(Icons.Default.Edit, null, tint = White, modifier = Modifier.size(16.dp))
                 }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Form
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                readOnly = true // Typically phone is hard to change or requires OTP
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.updateProfile(name, email, if (phone != state.user?.phone) phone else null) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (state.isLoading) {
                    androidx.compose.material3.CircularProgressIndicator(color = White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Save Changes", color = White)
                }
            }
        }
    }
}
