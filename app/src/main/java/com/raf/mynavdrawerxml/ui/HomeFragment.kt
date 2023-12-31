package com.raf.mynavdrawerxml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.raf.mynavdrawerxml.R
import com.raf.mynavdrawerxml.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonHandler()
        // Add More Action
    }

    private fun buttonHandler() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}