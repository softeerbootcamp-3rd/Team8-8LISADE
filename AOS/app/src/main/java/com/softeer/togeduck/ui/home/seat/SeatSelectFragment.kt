package com.softeer.togeduck.ui.home.seat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentSeatSelectBinding

class SeatSelectFragment : Fragment() {
    private var _binding: FragmentSeatSelectBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_seat_select, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        val seatSelectView = binding.seatSelectView
        val selectSeat = seatSelectView.selectSeat

        selectSeat.observe(viewLifecycleOwner, Observer {
            var vgColor = R.color.gray300
            var selectCompleteBtnEnabled = false

            if (selectSeat.value == true) {
                vgColor = R.color.main
                selectCompleteBtnEnabled = true
            }

            binding.run {
                selectCompleteBtn.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(), vgColor
                    )
                )
                selectCompleteBtn.isEnabled = selectCompleteBtnEnabled
            }
        })
    }
}