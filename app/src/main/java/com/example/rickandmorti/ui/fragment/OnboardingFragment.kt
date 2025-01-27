package com.example.rickandmorti.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.rickandmorti.MainActivity
import com.example.rickandmorti.data.SharedPreference
import com.example.rickandmorti.databinding.FragmentOnboardingBinding
import com.example.rickandmorti.ui.adapter.OnboardingAdapter
import com.example.rickandmorti.ui.onboarding.OnboardingViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val viewModel: OnboardingViewModel by viewModels()

    @Inject
    lateinit var pref: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)

        val adapter = OnboardingAdapter(viewModel.pages)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.buttonSkip.setOnClickListener {
            pref.isOnboardShown = true
            viewModel.completeOnboarding()
            navigateToMainScreen()
        }

        binding.buttonNext.setOnClickListener {
            if (binding.viewPager.currentItem < viewModel.pages.size - 1) {
                binding.viewPager.currentItem++
            } else {
                pref.isOnboardShown = true
                viewModel.completeOnboarding()
                navigateToMainScreen()
            }
        }

        return binding.root
    }

    private fun navigateToMainScreen() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }
}