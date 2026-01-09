package com.combocart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combocart.common.Resource
import com.combocart.data.remote.RecipeDto
import com.combocart.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchState(
    val isLoading: Boolean = false,
    val searchResults: List<RecipeDto> = emptyList(),
    val error: String? = null,
    val query: String = ""
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")

    init {
        @OptIn(FlowPreview::class)
        _query
            .debounce(500L)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isNotEmpty()) {
                    searchRecipes(query)
                } else {
                    _state.value = _state.value.copy(searchResults = emptyList(), query = "")
                }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        _state.value = _state.value.copy(query = newQuery)
    }

    private fun searchRecipes(query: String) {
        viewModelScope.launch {
            repository.getRecipes(search = query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(searchResults = result.data ?: emptyList(), isLoading = false)
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
