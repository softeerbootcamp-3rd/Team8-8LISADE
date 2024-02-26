package com.softeer.togeduck.ui.reserve_status.reserve_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentSeatChartDialogueBinding
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatChartFragmentDialogue : DialogFragment() {
    private var _binding: FragmentSeatChartDialogueBinding? = null
    private val binding get() = _binding!!
    private val args: SeatChartFragmentDialogueArgs by navArgs()
    private val seatChartViewModel: SeatChartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_seat_chart_dialogue, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.9).toInt()

        dialog?.window?.setLayout(
            width, height
        )
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(), R.drawable.dialogue_radius
            )
        )

        val routeId = args.routeId
        seatChartViewModel.routeId = routeId

        binding.iconClose.setOnClickListener {
            dialog?.dismiss()
        }

        seatChartViewModel.loadSeatChartData()
        seatChartViewModel.errMessage.observe(viewLifecycleOwner) {
            showErrorToast(requireContext(), it.toString())
        }
    }


}