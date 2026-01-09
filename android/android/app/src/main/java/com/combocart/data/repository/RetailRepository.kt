package com.combocart.data.repository

import androidx.compose.ui.graphics.Color
import com.combocart.ui.home.RetailStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetailRepository @Inject constructor() {
    private val _stores = MutableStateFlow(
        listOf(
            RetailStore("1", "Fresh Mart Grocery", 4.8, "250+", "0.8 km away", "15-20 min", "Open", Color(0xFFFFCCBC)),
            RetailStore("2", "Green Valley Organics", 4.9, "150+", "1.2 km away", "20-25 min", "Open", Color(0xFFC8E6C9)),
            RetailStore("3", "Super Bazaar", 4.6, "320+", "2.1 km away", "Opens at 9 AM", "Closed", Color(0xFFFFF9C4)),
            RetailStore("4", "Farm Fresh Store", 4.7, "185+", "1.5 km away", "18-22 min", "Open", Color(0xFFD1FAE5))
        )
    )
    val stores: StateFlow<List<RetailStore>> = _stores.asStateFlow()

    fun addStore(store: RetailStore) {
        _stores.value = listOf(store) + _stores.value
    }
    
    fun getStoreById(id: String): RetailStore? {
        return _stores.value.find { it.id == id }
    }
}
