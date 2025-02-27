package com.example.bookmyslot.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookmyslot.database.AppDatabase
import com.example.bookmyslot.databinding.FragmentAddInterviewSlotBinding
import com.example.bookmyslot.databinding.FragmentRegisterBinding
import com.example.bookmyslot.model.InterviewSlot
import com.example.service.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class AddInterviewSlotFragment : Fragment() {

    private lateinit var binding: FragmentAddInterviewSlotBinding

    //this is a list of all available job roles
    private val jobRoles = listOf(
        "Android Developer", "iOS Developer", "Backend Developer",
        "Frontend Developer", "DevOps Engineer", "QA Engineer",
        "Data Scientist", "Cybersecurity Specialist", "Cloud Engineer", "Full Stack Developer"
    )

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddInterviewSlotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Attaching dropdown of jobroles here
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, jobRoles)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.jobRoleSpinner.adapter = spinnerAdapter

        //Added date picker to select a particular date
        binding.selectDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                    binding.selectDateButton.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        //Added time picker to select particular available time
        binding.selectTimeButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                    binding.selectTimeButton.text = selectedTime
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.addSlotButton.setOnClickListener {
            addInterviewSlot()
        }
    }

    // Function to add interview slots to the database
    private fun addInterviewSlot() {
        val jobRole = binding.jobRoleSpinner.selectedItem as String
        val interviewName = binding.interviewNameEditText.text.toString().trim()
        val designation = binding.designationEditText.text.toString().trim()


        if (interviewName.isEmpty() || designation.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = UserSession.getInstance().loggedInUserId
        if (userId == -1) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val slot = InterviewSlot(
            userId = userId,
            jobRole = jobRole,
            interviewName = interviewName,
            designation = designation,
            date = selectedDate,
            time = selectedTime
        )

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(requireContext())
            db.interviewSlotDao().insertInterviewSlot(slot)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Interview slot added successfully", Toast.LENGTH_SHORT).show()
                clearFields()
                findNavController().popBackStack()
            }
        }
    }

    // function to clear all fields after adding slot
    private fun clearFields() {
        binding.interviewNameEditText.text.clear()
        binding.designationEditText.text.clear()
        binding.selectDateButton.text = "Select Date"
        binding.selectTimeButton.text = "Select Time"
        selectedDate = ""
        selectedTime = ""
        binding.jobRoleSpinner.setSelection(0)
    }
}
