package com.softeer.togeduck.ui.home.seat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentSeatPaymentBinding
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatPaymentFragment : Fragment() {
    private var _binding: FragmentSeatPaymentBinding? = null
    private val binding get() = _binding!!
    private val args: SeatPaymentFragmentArgs by navArgs()
    private val seatPaymentViewModel: SeatPaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeatPaymentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.paymentBtn.setOnClickListener {
            seatPaymentViewModel.setMySeatData()
            findNavController().navigate(R.id.action_seatPaymentFragment_to_seatPaymentCompleteFragment)
        }

        val routeId = args.routeId
        val selectedSeatNum = args.selectedSeatNum
        seatPaymentViewModel.routeId = routeId
        seatPaymentViewModel.selectedNum = selectedSeatNum

        seatPaymentViewModel.loadSeatPaymentData()
        seatPaymentViewModel.errMessage.observe(viewLifecycleOwner) {
            showErrorToast(requireContext(), it.toString())
        }

    }
}