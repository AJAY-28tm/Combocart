package com.combocart.ui.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combocart.common.Resource
import com.combocart.data.remote.RecipeDto
import com.combocart.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: RecipeDto? = null,
    val error: String? = null
)

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeDetailState())
    val state: StateFlow<RecipeDetailState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("recipeId")?.let { recipeId ->
            getRecipe(recipeId)
        }
    }

    private fun getRecipe(id: String) {
        viewModelScope.launch {
            repository.getRecipeById(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = RecipeDetailState(recipe = result.data)
                    }
                    is Resource.Error -> {
                        _state.value = RecipeDetailState(error = result.message)
                    }
                    is Resource.Loading -> {
                        _state.value = RecipeDetailState(isLoading = true)
                    }
                }
            }
        }
    }
}
