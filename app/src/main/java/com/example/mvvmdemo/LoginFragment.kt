package com.example.mvvmdemo

import android.R.attr.name
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
import com.example.mvvmdemo.api.LoginService
import com.example.mvvmdemo.api.RetrofitHelper
import com.example.mvvmdemo.databinding.FragmentLoginBinding
import com.example.mvvmdemo.model.RegisterResponse
import com.example.mvvmdemo.repository.LoginRepository
import com.example.mvvmdemo.viewModel.LoginViewModel
import com.example.mvvmdemo.viewModel.LoginViewModelFactory
import com.google.gson.Gson


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginRepository: LoginRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val loginService = RetrofitHelper.getInstance().create(LoginService::class.java)
        loginRepository = LoginRepository(loginService)

        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(loginRepository)
        )[LoginViewModel::class.java]

        loginUser()

        binding.btnLogin.setOnClickListener {
            validateFields()
        }
        return binding.root;
    }

    private fun validateFields() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            showToast("All fields must be filled.")
            return
        }

        if (!isValidEmail(email)) {
            showToast("Invalid email address.")
            return
        }

        binding.progressBarLogin.visibility = View.VISIBLE
        // All validations passed, you can proceed with further actions
        loginViewModel.loginUser(email, password)
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun loginUser() {

        loginViewModel.loginUser.observe(requireActivity(), Observer {

            if (it.success) {
                binding.progressBarLogin.visibility = View.GONE
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                val action = LoginFragmentDirections.actionLoginFragmentToMainFragment(
                    it.result!!.id.toString(),
                    it.result.name,
                    it.result.email,
                    it.result.phone
                )
                findNavController().navigate(action)
            } else {
                binding.progressBarLogin.visibility = View.GONE
            }
        })

        loginViewModel.loginError.observe(requireActivity(), Observer {
            binding.progressBarLogin.visibility = View.GONE
            val gson = Gson()
            val apiError: RegisterResponse = gson.fromJson(it, RegisterResponse::class.java)
            val errorMessage: List<String> = apiError.errors
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(requireActivity(), errorMessage[0].toString(), Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireActivity(), apiError.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}