package com.example.multistepregistrationsample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.multistepregistrationsample.R
import com.example.multistepregistrationsample.data.FarmerDetails
import com.example.multistepregistrationsample.data.workmanager.enqueueSyncWork
import com.example.multistepregistrationsample.databinding.FragmentFarmerDetailsBinding
import com.example.multistepregistrationsample.viewmodel.FarmerRegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FarmerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFarmerDetailsBinding
    private val viewModel: FarmerRegistrationViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFarmerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //viewModel.syncOfflineData()
        enqueueSyncWork(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonContinue.setOnClickListener {

            val details = FarmerDetails(
                firstName = binding.inputFirstName.text.toString(),
                middleName = binding.inputMiddleName.text.toString(),
                lastName =  binding.inputLastName.text.toString(),
                idNumber =  binding.inputIDNum.text.toString()
            )

            viewModel.updateFarmerDetails(details)
            findNavController().navigate(R.id.action_farmerDetailsFragment_to_contactDetailsFragment)
        }

        viewModel.farmerRegistrationData.observe(viewLifecycleOwner) { data ->
            // Update UI with farmer data

            Log.d("FarmerDetailsFragment","${data.farmerDetails}")
        }

        viewModel.registrationResult.observe(viewLifecycleOwner) { result->

            Log.d("Farmer Details","Saved success $result")
        }
    }
}