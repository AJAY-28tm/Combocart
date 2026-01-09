package com.combocart.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.combocart.ui.onboarding.OnboardingScreen
import com.combocart.ui.onboarding.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController)
        }
        composable(Screen.Login.route) {
            com.combocart.ui.auth.LoginScreen(navController)
        }
        composable(
            route = Screen.Signup.route,
            arguments = listOf(navArgument("phone") { type = NavType.StringType })
        ) { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            com.combocart.ui.auth.SignupScreen(navController, phone)
        }
        composable(
            route = Screen.LoginPassword.route,
            arguments = listOf(navArgument("phone") { type = NavType.StringType })
        ) { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            com.combocart.ui.auth.LoginPasswordScreen(phone, navController)
        }
        composable(Screen.LocationSetup.route) {
            com.combocart.ui.setup.LocationPermissionScreen(navController)
        }
        composable(Screen.TasteProfile.route) {
            com.combocart.ui.setup.TasteProfileScreen(navController)
        }
        composable(Screen.DietaryPreferences.route) {
            com.combocart.ui.setup.DietaryPreferencesScreen(navController)
        }
        composable(Screen.AllergySetup.route) {
            com.combocart.ui.setup.AllergySetupScreen(navController)
        }
        composable(Screen.KitchenReady.route) {
            com.combocart.ui.setup.KitchenReadyScreen(navController)
        }
        composable(Screen.Home.route) {
            com.combocart.ui.home.HomeScreen(navController)
        }
        composable(Screen.Search.route) {
            com.combocart.ui.home.SearchScreen(navController)
        }
        composable(Screen.Cravings.route + "/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            com.combocart.ui.home.CravingsScreen(navController, category)
        }
        composable(Screen.Notifications.route) {
            com.combocart.ui.home.NotificationsScreen(navController)
        }
        composable(Screen.RetailHome.route) {
            com.combocart.ui.home.RetailHomeScreen(navController)
        }
        composable(Screen.RetailRegister.route) {
            com.combocart.ui.home.RetailRegisterScreen(navController)
        }
        composable(
            Screen.RetailStoreDetail.route,
            arguments = listOf(navArgument("storeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val storeId = backStackEntry.arguments?.getString("storeId") ?: ""
            com.combocart.ui.home.RetailStoreDetailScreen(navController, storeId)
        }
        
        // Module 6
        composable(Screen.Cart.route) { 
             com.combocart.ui.cart.CartScreen(navController) 
        }
        composable(Screen.Checkout.route) { backStackEntry ->
             // For simplicity in this flat structure, let's just use standard Hilt injection 
             // but if we want to share, we should use a shared scope.
             // Actually, let's just use the backStackEntry of the first checkout screen for now
             // or just standard hiltViewModel() and handle data carefully.
             com.combocart.ui.checkout.CheckoutScreen(navController)
        }
        composable(Screen.Payment.route) {
             com.combocart.ui.checkout.PaymentScreen(navController)
        }
        composable(Screen.OrderConfirmed.route) {
             com.combocart.ui.checkout.OrderConfirmedScreen(navController)
        }
        composable(Screen.Orders.route) { 
             com.combocart.ui.orders.OrdersScreen(navController)
        }
        
        composable(
            route = Screen.TrackOrder.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) {
             com.combocart.ui.orders.TrackOrderScreen(navController)
        }

        composable(
            route = Screen.OrderDetail.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) {
             // Placeholder for OrderDetail
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                 Text("Order Detail Screen coming soon")
             }
        }

        composable(Screen.Profile.route) { 
             com.combocart.ui.profile.ProfileScreen(navController) 
        }
        
        // Profile Sub-features
        composable(Screen.EditProfile.route) { 
             com.combocart.ui.profile.EditProfileScreen(navController) 
        }
        composable(Screen.ManageAddress.route) { 
             com.combocart.ui.profile.ManageAddressScreen(navController) 
        }
        composable(Screen.Wallet.route) { 
             com.combocart.ui.profile.WalletScreen(navController) 
        }
        composable(Screen.Badges.route) { 
             com.combocart.ui.profile.BadgesScreen(navController) 
        }
        composable(Screen.Favorites.route) { 
             com.combocart.ui.profile.FavoritesScreen(navController) 
        }
        composable(Screen.Settings.route) { 
             com.combocart.ui.profile.SettingsScreen(navController) 
        }
        composable(Screen.Support.route) { 
             com.combocart.ui.profile.SupportScreen(navController) 
        }
        composable(Screen.Premium.route) {
             com.combocart.ui.profile.PremiumScreen(navController)
        }
        composable(Screen.RecipeDetail.route) { backStackEntry ->
             val recipeId = backStackEntry.arguments?.getString("recipeId")
             com.combocart.ui.recipe.RecipeDetailScreen(navController, recipeId)
        }
        composable(Screen.Nutrition.route) {
             com.combocart.ui.recipe.NutritionScreen(navController)
        }
        composable(Screen.Serves.route) {
             com.combocart.ui.recipe.ServesScreen(navController)
        }
        composable(Screen.Customize.route) {
             com.combocart.ui.recipe.CustomizeScreen(navController)
        }
        composable(Screen.Reviews.route) { backStackEntry ->
             val recipeId = backStackEntry.arguments?.getString("recipeId")
             com.combocart.ui.recipe.ReviewScreen(navController, recipeId)
        }
    }
}
