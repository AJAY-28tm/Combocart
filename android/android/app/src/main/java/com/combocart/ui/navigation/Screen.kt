package com.combocart.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Signup : Screen("signup/{phone}") {
        fun createRoute(phone: String) = "signup/$phone"
    }
    object LoginPassword : Screen("login_password/{phone}") {
        fun createRoute(phone: String) = "login_password/$phone"
    }
    object Home : Screen("home")
    object LocationSetup : Screen("setup_location")
    object TasteProfile : Screen("setup_taste")
    object DietaryPreferences : Screen("setup_dietary")
    object AllergySetup : Screen("setup_allergies")
    object KitchenReady : Screen("setup_complete")
    
    // Bottom Nav
    object Search : Screen("search")
    object Cart : Screen("cart")
    object Orders : Screen("orders")
    object Profile : Screen("profile")
    
    // Other Home Module Screens
    object Cravings : Screen("cravings") {
        fun createRoute(category: String) = "cravings/$category"
    }
    object Notifications : Screen("notifications")
    
    // Module 4
    object RecipeDetail : Screen("recipe/{recipeId}") {
        fun createRoute(recipeId: String) = "recipe/$recipeId"
    }
    object Nutrition : Screen("recipe/{recipeId}/nutrition") {
        fun createRoute(recipeId: String) = "recipe/$recipeId/nutrition"
    }
    object Serves : Screen("recipe/{recipeId}/serves") {
        fun createRoute(recipeId: String) = "recipe/$recipeId/serves"
    }
    object Reviews : Screen("recipe/{recipeId}/reviews") {
        fun createRoute(recipeId: String) = "recipe/$recipeId/reviews"
    }
    object Customize : Screen("recipe/{recipeId}/customize") {
        fun createRoute(recipeId: String) = "recipe/$recipeId/customize"
    }
    
    // Module 6
    object Checkout : Screen("checkout")
    object Payment : Screen("checkout/payment")
    object OrderConfirmed : Screen("order/confirmed")
    
    // Module 7
    object TrackOrder : Screen("order/track/{orderId}") {
        fun createRoute(orderId: String) = "order/track/$orderId"
    }
    object OrderDetail : Screen("order/detail/{orderId}")
    
    // Profile Sub-screens
    object EditProfile : Screen("profile/edit")
    object ManageAddress : Screen("profile/address")
    object Wallet : Screen("profile/wallet")
    object Badges : Screen("profile/badges")
    object Favorites : Screen("profile/favorites")
    object Support : Screen("profile/support")
    object Settings : Screen("profile/settings")
    object Premium : Screen("profile/premium")

    // Retail Module
    object RetailHome : Screen("retail/home")
    object RetailRegister : Screen("retail/register")
    object RetailStoreDetail : Screen("retail/store/{storeId}") {
        fun createRoute(storeId: String) = "retail/store/$storeId"
    }

    // Add other routes as needed
}
