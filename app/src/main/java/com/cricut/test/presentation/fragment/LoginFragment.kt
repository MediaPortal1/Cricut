package com.cricut.test.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cricut.test.R
import com.cricut.test.databinding.FragmentLoginBinding
import com.cricut.test.domain.AuthResult
import com.cricut.test.presentation.vm.LoginVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val vm: LoginVM by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) =
        FragmentLoginBinding.inflate(layoutInflater).apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        startObserving()
    }

    private fun startObserving() {
        vm.authResult.observe(viewLifecycleOwner) { res ->
            when (res) {
                AuthResult.FAILED -> onLoginFailed()
                AuthResult.SUCCESS -> onLoginSuccess()
                AuthResult.PROGRESS -> Unit
            }
        }
    }

    private fun initViews() {
        binding.btnLogin.setOnClickListener {
            vm.onLoginClicked(binding.editUsername.text.toString(), binding.editPass.text.toString())
        }
    }

    private fun onLoginFailed() {
        view?.let { Snackbar.make(it, R.string.login_failed, Snackbar.LENGTH_SHORT).show() }
    }

    private fun onLoginSuccess() {
        findNavController().navigate(R.id.blank_page)
    }
}