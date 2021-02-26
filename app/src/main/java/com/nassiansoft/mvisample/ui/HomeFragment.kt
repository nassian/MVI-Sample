package com.nassiansoft.mvisample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nassiansoft.mvisample.R
import com.nassiansoft.mvisample.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment:Fragment() {

    private val factory:HomeVmFactory by inject()
    private val viewModel by viewModels<HomeViewModel> { factory  }
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        renderState()
        renderViewEffect()
    }

    private fun renderViewEffect() {
        lifecycleScope.launch {
            viewModel.viewEffectFlow.collect {
                when (it) {
                    is HomeViewEffect.Error -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderState() {

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    HomeViewState.Idle -> {
                        binding.homeProgressBar.visibility = View.INVISIBLE
                        binding.homeUserInfoTextView.text = "idle state"
                    }
                    HomeViewState.Loading -> {
                        binding.homeProgressBar.visibility = View.VISIBLE
                        binding.homeUserInfoTextView.text = "loading state"
                    }
                    is HomeViewState.Data -> {
                        binding.homeProgressBar.visibility = View.INVISIBLE
                        val txt="data state\n"+it.user.toString()
                        binding.homeUserInfoTextView.text = txt
                    }
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.homeGetUserButton.setOnClickListener {
            val id=binding.homeUserIdEditText.text.toString().toIntOrNull()
            id?.let {
                sendIntent(HomeViewIntent.GetUser(it))
            }
        }
    }

    private fun sendIntent(homeViewIntent: HomeViewIntent){
        lifecycleScope.launch {
            viewModel.intent.send(homeViewIntent)
        }
    }


}