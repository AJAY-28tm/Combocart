package com.combocart.ui.home

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

data class CravingsState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeDto> = emptyList(),
    val error: String? = null,
    val category: String = ""
)

@HiltViewModel
class CravingsViewModel @Inject constructor(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CravingsState())
    val state: StateFlow<CravingsState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("category")?.let { category ->
            _state.value = _state.value.copy(category = category)
            getCravings(category)
        }
    }

    fun getCravings(category: String) {
        viewModelScope.launch {
            repository.getRecipes(category = category).collect { result ->
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
