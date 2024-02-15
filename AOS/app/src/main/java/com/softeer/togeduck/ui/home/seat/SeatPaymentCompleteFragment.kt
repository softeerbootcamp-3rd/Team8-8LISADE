package com.softeer.togeduck.ui.home.seat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentSeatPaymentCompleteBinding

class SeatPaymentCompleteFragment : Fragment() {
    private var _binding: FragmentSeatPaymentCompleteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeatPaymentCompleteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.run {
            moveHomeBtn.setOnClickListener {
                findNavController().navigate(R.id.action_seatPaymentCompleteFragment_to_mainActivity)
            }
            moveReserveStatusBtn.setOnClickListener {
                findNavController().navigate(R.id.action_seatPaymentCompleteFragment_to_reserveStatusDetailActivity)
            }
        }
    }
}