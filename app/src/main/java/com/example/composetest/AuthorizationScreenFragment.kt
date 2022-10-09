package com.example.composetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composetest.databinding.FragmentOneBinding

class AuthorizationScreenFragment : Fragment() {
    private val viewModel by viewModels<AuthorizationScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentOneBinding
        .inflate(layoutInflater, container, false)
        .apply {
            composeView.setContent {
                val model by viewModel.modelFlow.collectAsState()
                AppCompose.AuthorizationScreen(model = model, onIntent = viewModel::onIntent)
            }
        }.root
}