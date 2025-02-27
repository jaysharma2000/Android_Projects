package com.example.bookmyslot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmyslot.adapter.InterviewSlotAdapter
import com.example.bookmyslot.database.AppDatabase
import com.example.bookmyslot.databinding.FragmentAddInterviewSlotBinding
import com.example.bookmyslot.databinding.FragmentViewAllSlotsBinding
import com.example.bookmyslot.model.InterviewSlot
import com.example.service.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewAllSlotsFragment : Fragment() {

    private lateinit var binding: FragmentViewAllSlotsBinding

    private lateinit var adapter: InterviewSlotAdapter
    private val slotsList = mutableListOf<InterviewSlot>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewAllSlotsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = InterviewSlotAdapter(slotsList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        //Added navigation for all slots to update the  details
        adapter.onItemClick = { slot ->
            val action = ViewAllSlotsFragmentDirections.actionViewAllSlotsFragmentToUpdateInterviewSlotFragment(slot)
            findNavController().navigate(action)
        }

        loadSlots()

        //Added the swipe to delete functionality to delete the added interview slot
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val slotToDelete = adapter.removeItem(position)
                lifecycleScope.launch(Dispatchers.IO) {
                    val db = AppDatabase.getInstance(requireContext())
                    db.interviewSlotDao().deleteInterviewSlot(slotToDelete)
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)

    }

    //Function to display all the added slots on UI
    private fun loadSlots() {
        val userId = UserSession.getInstance().loggedInUserId
        if (userId == -1) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(requireContext())
            val slots = db.interviewSlotDao().getSlotsForUser(userId)
            withContext(Dispatchers.Main) {
                adapter.updateData(slots)
            }
        }
    }
}
