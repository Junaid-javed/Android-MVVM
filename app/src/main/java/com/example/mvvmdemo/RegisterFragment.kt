package com.example.mvvmdemo

import android.opengl.Visibility
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mvvmdemo.api.RegisterService
import com.example.mvvmdemo.api.RetrofitHelper
import com.example.mvvmdemo.databinding.FragmentRegisterBinding
import com.example.mvvmdemo.model.RegisterResponse
import com.example.mvvmdemo.repository.RegisterRepository
import com.example.mvvmdemo.viewModel.RegisterViewModel
import com.example.mvvmdemo.viewModel.RegisterViewModelFactory
import com.google.gson.Gson


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerRepository: RegisterRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val registerService = RetrofitHelper.getInstance().create(RegisterService::class.java)
        registerRepository = RegisterRepository(registerService)

        registerViewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(registerRepository)
        )[RegisterViewModel::class.java]

        registerUser()

        binding.btnRegister.setOnClickListener {
            validateFields()
          //  findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }

    private fun registerUser() {
        registerViewModel.registerUser.observe(requireActivity(), Observer {
            if (it.success) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        registerViewModel.errorData.observe(requireActivity(), Observer {
            binding.progressBar.visibility = View.GONE
            val gson = Gson()
            val apiError: RegisterResponse = gson.fromJson(it, RegisterResponse::class.java)
            val errorMessage: List<String> = apiError.errors
            Toast.makeText(requireActivity(), errorMessage[0], Toast.LENGTH_LONG).show()
        })
    }

    private fun validateFields() {
        val username = binding.username.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.passwordConfirm.text.toString()
        val phone = binding.phone.text.toString()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || confirmPassword.isEmpty()) {
            showToast("All fields must be filled.")
            return
        }

        if (!isValidEmail(email)) {
            showToast("Invalid email address.")
            return
        }


        if (password.length < 8) {
            showToast("Password must be at least 8 characters.")
            return
        }
        if (confirmPassword.length < 8) {
            showToast("Password must be at least 8 characters.")
            return
        }

        if (password != confirmPassword) {
            showToast("Passwords do not match.")
            return
        }

        // All validations passed, you can proceed with further actions
        binding.progressBar.visibility = View.VISIBLE
        registerViewModel.registerUser(username, email, phone, password, confirmPassword)
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}