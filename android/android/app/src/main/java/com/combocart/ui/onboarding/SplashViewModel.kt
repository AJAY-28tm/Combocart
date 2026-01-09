package com.combocart.ui.onboarding

import androidx.lifecycle.ViewModel
import com.combocart.common.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val sessionManager: SessionManager
) : ViewModel()
