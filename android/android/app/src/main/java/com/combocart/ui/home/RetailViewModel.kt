package com.combocart.ui.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class RetailStore(
    val id: String,
    val name: String,
    val rating: Double,
    val reviews: String,
    val distance: String,
    val time: String,
    val status: String,
    val iconBg: Color
)

data class RetailState(
    val stores: List<RetailStore> = listOf(
        RetailStore("1", "Fresh Mart Grocery", 4.8, "250+", "0.8 km away", "15-20 min", "Open", Color(0xFFFFCCBC)),
        RetailStore("2", "Green Valley Organics", 4.9, "150+", "1.2 km away", "20-25 min", "Open", Color(0xFFC8E6C9)),
        RetailStore("3", "Super Bazaar", 4.6, "320+", "2.1 km away", "Opens at 9 AM", "Closed", Color(0xFFFFF9C4)),
        RetailStore("4", "Farm Fresh Store", 4.7, "185+", "1.5 km away", "18-22 min", "Open", Color(0xFFD1FAE5))
    )
)

@HiltViewModel
class RetailViewModel @Inject constructor(
    private val repository: com.combocart.data.repository.RetailRepository
) : ViewModel() {
    
    val state: StateFlow<RetailState> = repository.stores.map { stores ->
        RetailState(stores = stores)
    }.stateIn(viewModelScope, SharingStarted.Lazily, RetailState())

    fun addStore(name: String, category: String, address: String) {
        val newStore = RetailStore(
            id = java.util.UUID.randomUUID().toString(),
            name = name,
            rating = 5.0,
            reviews = "New",
            distance = "0.5 km away",
            time = "10-15 min",
            status = "Open",
            iconBg = Color(0xFFE0E0E0)
        )
        repository.addStore(newStore)
    }
}
