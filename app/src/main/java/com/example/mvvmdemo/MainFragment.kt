package com.example.mvvmdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.mvvmdemo.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val args: MainFragmentArgs by navArgs()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.id.text = "Id: ${args.id}"
        binding.name.text = "Name: ${args.name}"
        binding.email.text = "Email: ${args.email}"
        binding.phone.text = "PhoneNo: ${args.phoneNo}"

        return binding.root
    }

}