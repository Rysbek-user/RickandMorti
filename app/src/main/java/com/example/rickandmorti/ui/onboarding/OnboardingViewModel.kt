package com.example.rickandmorti.ui.onboarding

import androidx.lifecycle.ViewModel
import com.example.rickandmorti.R
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    val pages = listOf(
        OnBoardingPage("Добро пожаловать", "Описание экрана 1", R.drawable.image1),
        OnBoardingPage("Функция 1", "Описание экрана 2", R.drawable.image2),
        OnBoardingPage("Функция 2", "Описание экрана 3", R.drawable.image3),
        OnBoardingPage("Начнем", "Описание экрана 4", R.drawable.image4)
    )

    fun completeOnboarding() {
    }
}