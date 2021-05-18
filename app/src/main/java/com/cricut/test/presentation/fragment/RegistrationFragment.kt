package com.cricut.test.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cricut.test.R
import com.cricut.test.databinding.FragmentRegistrationBinding
import com.cricut.test.domain.PutResult
import com.cricut.test.domain.UserData
import com.cricut.test.presentation.vm.RegistrationVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val vm: RegistrationVM by viewModels()

    private val userPass get() = binding.editPass.text.toString()
    private val userName get() = binding.editUsername.text.toString()
    private val userEmail get() = binding.editEmail.text.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) =
        FragmentRegistrationBinding.inflate(inflater).apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        startObserving()
    }

    private fun startObserving() {
        vm.createUserState.observe(viewLifecycleOwner){ state ->
            when(state){
                PutResult.EXIST -> Snackbar.make(binding.root, R.string.already_registered, Snackbar.LENGTH_LONG).show()
                PutResult.SUCCESS -> findNavController().navigate(R.id.registration_to_blank_action)
                PutResult.PROGRESS -> Unit
                PutResult.NOT_VALID -> Snackbar.make(binding.root, R.string.error_validation, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun initViews() {
        binding.btnRegister.setOnClickListener {
            vm.onCreateNewProfile(UserData(userName, userEmail, userPass))
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.already_registered_action)
        }
    }
}