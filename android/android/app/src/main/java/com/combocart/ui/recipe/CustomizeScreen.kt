package com.combocart.ui.recipe

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.combocart.ui.setup.TasteSliderItem
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

@Composable
fun CustomizeScreen(navController: NavController) {
    var spiceLevel by remember { mutableStateOf(0.5f) }
    var specialRequest by remember { mutableStateOf("") }
    
    val extras = listOf(
        "Extra Cheese (+₹50)",
        "Extra Veggies (+₹30)",
        "Extra Spice Mix (+₹25)"
    )
    val extrasState = remember { mutableStateListOf(false, false, false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        
        Text("Make it yours", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Black)
        Text("Customize Butter Chicken Combo to your taste", fontSize = 14.sp, color = MediumGray)

        Spacer(modifier = Modifier.height(32.dp))

        // Reuse TasteSliderItem from Setup
        TasteSliderItem(
            label = "Spice Level",
            icon = "🌶️",
            value = spiceLevel,
            onValueChange = { spiceLevel = it },
            accentColor = Color.Red
        )
        
        // Simple visual indicator for level (Mild/Medium/Hot)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
             Text("Mild", fontSize = 12.sp, color = MediumGray)
             Text("Medium", fontSize = 12.sp, color = PrimaryOrange, fontWeight = FontWeight.Bold)
             Text("Hot", fontSize = 12.sp, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Add Extra", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        
        extras.forEachIndexed { index, extra ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = extrasState[index],
                    onCheckedChange = { extrasState[index] = it },
                    colors = CheckboxDefaults.colors(checkedColor = PrimaryOrange)
                )
                Text(extra)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Special Requests", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        OutlinedTextField(
            value = specialRequest,
            onValueChange = { specialRequest = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Any special requests? (e.g., less oil)") },
            minLines = 3
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Save Customization", color = White)
        }
    }
}
