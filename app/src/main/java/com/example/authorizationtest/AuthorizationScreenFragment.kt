package com.example.authorizationtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composetest.databinding.FragmentAuthorizationScreenBinding

class AuthorizationScreenFragment : Fragment() {
    private val viewModel by viewModels<AuthorizationScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAuthorizationScreenBinding
        .inflate(inflater, container, false)
        .apply {
            cvAuthorizationScreen.setContent {
                val model by viewModel.modelFlow.collectAsState()

                AppCompose.AuthorizationScreen(
                    model = model,
                    onIntent = viewModel::onIntent,
                    onCLickAddButton = {
                        viewModel.addNewAuthorizedUser(
                            model.surNameField,
                            model.nameField,
                            model.patronymicField,
                            model.sexField,
                            model.subscribedCheckbox
                        )
                        viewModel.clearAllFields()
                    }
                )
            }
        }
        .root
}