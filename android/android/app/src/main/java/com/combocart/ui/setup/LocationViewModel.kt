package com.combocart.ui.setup

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import android.location.Geocoder
import com.combocart.common.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import java.util.Locale

data class LocationState(
    val location: Location? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val pincode: String? = null
)

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val sessionManager: SessionManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(LocationState())
    val state = _state.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                var result = fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                ).await()
                
                if (result == null) {
                    result = fusedLocationClient.lastLocation.await()
                }
                
                if (result != null) {
                    _state.value = _state.value.copy(location = result)
                    
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(result.latitude, result.longitude, 1)
                    
                    if (!addresses.isNullOrEmpty()) {
                        val addr = addresses[0]
                        val fullAddress = addr.getAddressLine(0) ?: "Location Fetched"
                        val city = addr.locality ?: addr.subAdminArea ?: ""
                        val state = addr.adminArea ?: ""
                        val pincode = addr.postalCode ?: ""
                        
                        _state.value = _state.value.copy(
                            address = fullAddress,
                            city = city,
                            state = state,
                            pincode = pincode,
                            isLoading = false
                        )
                        
                        sessionManager.saveLocation(fullAddress)
                    } else {
                        _state.value = _state.value.copy(address = "Location Fetched", isLoading = false)
                    }
                    onSuccess()
                } else {
                    _state.value = _state.value.copy(error = "Could not fetch location. Please ensure GPS is on.", isLoading = false)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "An error occurred", isLoading = false)
            }
        }
    }
}
