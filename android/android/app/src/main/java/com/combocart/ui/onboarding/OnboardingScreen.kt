package com.combocart.ui.onboarding

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.R
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.DarkGray
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import kotlinx.coroutines.launch

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState) { page ->
            OnboardingPage(page = page)
        }

        // Bottom Controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pager Indicators
            Row(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(2) { iteration ->
                    val color = if (pagerState.currentPage == iteration) PrimaryOrange else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(10.dp)
                            .background(color, CircleShape)
                    )
                }
            }

            // Next / Get Started Button
            Button(
                onClick = {
                    if (pagerState.currentPage == 0) {
                        scope.launch { pagerState.animateScrollToPage(1) }
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage == 0) "Next >" else "Let's Get Started >",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun OnboardingPage(page: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Onboarding Images
        Image(
            painter = painterResource(
                id = if (page == 0) R.drawable.splash_1 else R.drawable.splash_2
            ),
            contentDescription = if (page == 0) "Complete Meal Kits" else "Fast Delivery",
            modifier = Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.height(32.dp))

        if (page == 0) {
            Text(
                text = "Complete Meal Kits",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryOrange
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Fresh ingredients measured perfectly for your recipes",
                fontSize = 14.sp,
                color = MediumGray,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = "At Your Door in 20 Minutes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                // Onboarding 2 has dark gradient background in spec, but keeping simple white for consistency first
                color = Color.Black 
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Lightning-fast delivery when hunger strikes",
                fontSize = 14.sp,
                color = MediumGray,
                textAlign = TextAlign.Center
            )
        }
    }
}
