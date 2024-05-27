package com.example.multistepregistrationsample.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.multistepregistrationsample.R
import com.example.multistepregistrationsample.data.ContactDetails
import com.example.multistepregistrationsample.databinding.FragmentContactDetailsBinding
import com.example.multistepregistrationsample.viewmodel.FarmerRegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment : Fragment() {

    private lateinit var binding: FragmentContactDetailsBinding
    private val viewModel: FarmerRegistrationViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.button.setOnClickListener {
           val details = ContactDetails(
               phoneNumber = binding.inputFirstName.text.toString(),
               farmManagerName = binding.inputMiddleName.text.toString(),
               farmManagerPhoneNumber = binding.inputLastName.text.toString(),
               village = binding.inputIDNum.text.toString()
           )

           viewModel.updateContactDetails(details)

           viewModel.submitRegistration()
       }

        viewModel.farmerRegistrationData.observe(viewLifecycleOwner) { data ->
            // Update UI with farmer data
            Log.d("ContactDetails","${data.contactDetails}")
        }

        viewModel.registrationResult.observe(viewLifecycleOwner) { result->

            Log.d("Contact Details","Saved success $result")
        }
    }
}