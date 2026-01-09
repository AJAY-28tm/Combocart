package com.combocart.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.theme.*

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import androidx.compose.ui.draw.clip
import com.combocart.ui.setup.LocationViewModel

import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetailRegisterScreen(
    navController: NavController,
    viewModel: RetailViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    locationViewModel: LocationViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    var storeName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var aadharNumber by remember { mutableStateOf("") }
    
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    
    var category by remember { mutableStateOf("Groceries") }
    var isAgreed by remember { mutableStateOf(false) }

    val locationState by locationViewModel.state.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            locationViewModel.getCurrentLocation()
        }
    }

    // Sync location details to form
    LaunchedEffect(locationState.address) {
        locationState.address?.let { address = it }
        locationState.city?.let { city = it }
        locationState.state?.let { state = it }
        locationState.pincode?.let { pincode = it }
    }
    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF9F9F9))
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Column {
                    Text("Retail Partner Registration", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Join ComboCart and grow your business.", fontSize = 12.sp, color = MediumGray)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                
                // Progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Step 1 of 2", fontSize = 12.sp, color = PrimaryOrange, fontWeight = FontWeight.Bold)
                    Text("Basic Details", fontSize = 12.sp, color = MediumGray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = 0.5f,
                    modifier = Modifier.fillMaxWidth().height(4.dp),
                    color = PrimaryOrange,
                    trackColor = Color(0xFFEEEEEE)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Section 1: Personal Info
                RegistrationSection(title = "Personal Information", icon = Icons.Default.Person) {
                    RegistrationTextField(label = "Store/Business Name *", value = storeName, onValueChange = { storeName = it }, icon = Icons.Default.Store)
                    RegistrationTextField(label = "Owner Name *", value = ownerName, onValueChange = { ownerName = it }, icon = Icons.Default.Person)
                    RegistrationTextField(label = "Phone Number *", value = phoneNumber, onValueChange = { phoneNumber = it }, icon = Icons.Default.Call)
                    RegistrationTextField(label = "Email Address *", value = emailAddress, onValueChange = { emailAddress = it }, icon = Icons.Default.Email)
                    RegistrationTextField(label = "Aadhar Number *", value = aadharNumber, onValueChange = { aadharNumber = it }, icon = Icons.Default.Info)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section 2: Store Location
                RegistrationSection(title = "Store Location", icon = Icons.Default.LocationOn) {
                    RegistrationTextField(label = "Store Address *", value = address, onValueChange = { address = it }, isMultiline = true)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        RegistrationTextField(label = "City *", value = city, onValueChange = { city = it }, modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(12.dp))
                        RegistrationTextField(label = "Pincode *", value = pincode, onValueChange = { pincode = it }, modifier = Modifier.weight(1f))
                    }
                    RegistrationTextField(label = "State *", value = state, onValueChange = { state = it }, icon = Icons.Default.KeyboardArrowDown)
                    
                    Button(
                        onClick = { 
                            permissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (locationState.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = White, strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Use Current Location", fontWeight = FontWeight.Bold)
                        }
                    }

                    if (locationState.location != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFEEEEEE))
                        ) {
                            val userPos = LatLng(locationState.location!!.latitude, locationState.location!!.longitude)
                            val cameraPositionState = rememberCameraPositionState {
                                position = CameraPosition.fromLatLngZoom(userPos, 15f)
                            }
                            GoogleMap(
                                modifier = Modifier.fillMaxSize(),
                                cameraPositionState = cameraPositionState
                            ) {
                                Marker(state = MarkerState(position = userPos))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section 3: Partnership Details
                RegistrationSection(title = "Partnership Details", icon = Icons.Default.AttachMoney) {
                    Text("Registration Fee *", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryOrange)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("One-time Registration", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Includes 50 FREE first delivery partnerships", fontSize = 11.sp, color = MediumGray)
                            }
                            Text("₹999", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = PrimaryOrange)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Delivery Partners *", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    
                    // Delivery Partners Selection
                    val partners = listOf("Dunzo", "Shadowfax", "Porter", "Own Delivery")
                    val selectedPartners = remember { mutableStateListOf<String>() }
                    
                    partners.forEach { partner ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (selectedPartners.contains(partner)) {
                                        selectedPartners.remove(partner)
                                    } else {
                                        selectedPartners.add(partner)
                                    }
                                }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedPartners.contains(partner),
                                onCheckedChange = { isChecked ->
                                    if (isChecked) selectedPartners.add(partner) else selectedPartners.remove(partner)
                                }
                            )
                            Text(partner, fontSize = 14.sp)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Store Category Dropdown
                    var expanded by remember { mutableStateOf(false) }
                    val categories = listOf("Groceries", "Fresh Produce", "Bakery", "Meat & Seafood", "Pet Supplies")
                    
                    Box {
                         RegistrationTextField(
                             label = "Store Category *", 
                             value = category, 
                             onValueChange = {}, 
                             icon = Icons.Default.KeyboardArrowDown,
                             readOnly = true,
                             onClick = { expanded = true }
                         )
                         DropdownMenu(
                             expanded = expanded,
                             onDismissRequest = { expanded = false },
                             modifier = Modifier.fillMaxWidth(0.8f).background(White)
                         ) {
                             categories.forEach { cat ->
                                 DropdownMenuItem(
                                     text = { Text(cat) },
                                     onClick = {
                                         category = cat
                                         expanded = false
                                     }
                                 )
                             }
                         }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // T&C
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isAgreed, onCheckedChange = { isAgreed = it })
                    Text(
                        "I agree to the Terms & Conditions and Privacy Policy of ComboCart.",
                        fontSize = 12.sp,
                        color = MediumGray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Submit
                Button(
                    onClick = { 
                        if (storeName.isNotEmpty() && isAgreed) {
                            viewModel.addStore(storeName, category, address)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                    shape = RoundedCornerShape(12.dp),
                    enabled = storeName.isNotEmpty() && isAgreed
                ) {
                    Text("Continue to Payment →", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun RegistrationSection(title: String, icon: ImageVector, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, tint = PrimaryOrange, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))
            content()
        }
    }
}

@Composable
fun RegistrationTextField(
    label: String, 
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector? = null, 
    modifier: Modifier = Modifier,
    isMultiline: Boolean = false,
    readOnly: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(modifier = modifier.padding(bottom = 16.dp)) {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DarkGray)
        Spacer(modifier = Modifier.height(6.dp))
        Box {
             OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth().clickable { if(readOnly) onClick() },
                trailingIcon = if (icon != null) { { Icon(icon, null, tint = MediumGray, modifier = Modifier.size(20.dp).clickable { if(readOnly) onClick() }) } } else null,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFEEEEEE),
                    focusedBorderColor = PrimaryOrange
                ),
                singleLine = !isMultiline,
                minLines = if (isMultiline) 3 else 1,
                readOnly = readOnly,
                enabled = !readOnly // Important for click-through
            )
            // Overlay so the click is captured by the Box if readOnly (since enabled=false blocks interactions)
            if (readOnly) {
                 Box(
                     modifier = Modifier
                         .matchParentSize()
                         .clickable(onClick = onClick)
                 )
            }
        }
    }
}
