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
import androidx.navigation.fragment.navArgs
import com.example.bookmyslot.database.AppDatabase
import com.example.bookmyslot.databinding.FragmentUpdateInterviewSlotBinding
import com.example.bookmyslot.model.InterviewSlot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class UpdateInterviewSlotFragment : Fragment() {

    private lateinit var binding: FragmentUpdateInterviewSlotBinding

    private val args: UpdateInterviewSlotFragmentArgs by navArgs()
    private lateinit var currentSlot: InterviewSlot

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    //Available job roles to update
    private val jobRoles = listOf(
        "Android Developer",
        "iOS Developer",
        "Backend Developer",
        "Frontend Developer",
        "DevOps Engineer",
        "QA Engineer",
        "Data Scientist",
        "Cybersecurity Specialist",
        "Cloud Engineer",
        "Full Stack Developer"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateInterviewSlotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentSlot = args.slot

        //attached spinner for job roles
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, jobRoles)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.jobRoleSpinner.adapter = spinnerAdapter
        val jobIndex = jobRoles.indexOf(currentSlot.jobRole)
        if (jobIndex >= 0) {
            binding.jobRoleSpinner.setSelection(jobIndex)
        }

        //prefilling all the details
        binding.interviewNameEditText.setText(currentSlot.interviewName)
        binding.designationEditText.setText(currentSlot.designation)
        selectedDate = currentSlot.date
        selectedTime = currentSlot.time
        binding.selectDateButton.text = selectedDate
        binding.selectTimeButton.text = selectedTime

        //listener for selected date
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

        //added listener for selected time
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

        binding.updateSlotButton.setOnClickListener {
            updateInterviewSlot()
        }
    }

    //Function to update the already added interview slot
    private fun updateInterviewSlot() {
        val jobRole = binding.jobRoleSpinner.selectedItem as String
        val interviewName = binding.interviewNameEditText.text.toString().trim()
        val designation = binding.designationEditText.text.toString().trim()

        if (interviewName.isEmpty() || designation.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedSlot = currentSlot.copy(
            jobRole = jobRole,
            interviewName = interviewName,
            designation = designation,
            date = selectedDate,
            time = selectedTime
        )

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(requireContext())
            db.interviewSlotDao().updateInterviewSlot(updatedSlot)
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Interview slot updated successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }
}
