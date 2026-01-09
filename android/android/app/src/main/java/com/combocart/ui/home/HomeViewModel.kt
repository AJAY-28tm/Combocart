package com.combocart.ui.home

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

data class HomeState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeDto> = emptyList(),
    val location: String = "Koramangala, Bangalore",
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val sessionManager: com.combocart.common.SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getRecipes()
        refreshLocation()
    }

    fun refreshLocation() {
        val lastLocation = sessionManager.fetchLocation()
        _state.value = _state.value.copy(location = lastLocation)
    }

    private fun getRecipes() {
        viewModelScope.launch {
            repository.getRecipes().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(recipes = result.data ?: emptyList(), isLoading = false)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = result.message, isLoading = false)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}
