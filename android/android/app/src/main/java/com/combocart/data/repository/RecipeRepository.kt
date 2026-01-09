package com.combocart.data.repository

import com.combocart.common.Resource
import com.combocart.data.remote.RecipeApi
import com.combocart.data.remote.RecipeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeApi: RecipeApi
) {
    fun getRecipes(search: String? = null, category: String? = null): Flow<Resource<List<RecipeDto>>> = flow {
        emit(Resource.Loading())
        try {
            val recipes = recipeApi.getRecipes(search = search, category = category)
            emit(Resource.Success(recipes))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    fun getRecipeById(id: String): Flow<Resource<RecipeDto>> = flow {
        emit(Resource.Loading())
        try {
            val recipe = recipeApi.getRecipeById(id)
            emit(Resource.Success(recipe))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
