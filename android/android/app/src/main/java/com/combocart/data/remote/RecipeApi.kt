package com.combocart.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class RecipeDto(
    val id: String,
    val title: String,
    val description: String?,
    val image_url: String?,
    val cooking_time_minutes: Int,
    val price: Double,
    val currency: String,
    val difficulty: String,
    val rating: Double,
    val is_vegetarian: Boolean
)

interface RecipeApi {
    @GET("recipes/")
    suspend fun getRecipes(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100,
        @Query("search") search: String? = null,
        @Query("category") category: String? = null
    ): List<RecipeDto>

    @GET("recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: String): RecipeDto
}
