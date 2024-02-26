package com.softeer.togeduck.ui.reserve_status.reserve_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentMobileTicketBinding
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MobileTicketFragment : Fragment() {
    private var _binding: FragmentMobileTicketBinding? = null
    private val binding get() = _binding!!
    private val mobileTicketViewModel: MobileTicketViewModel by viewModels()
    private val args: MobileTicketFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_mobile_ticket,
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
        val routeId = args.routeId
        mobileTicketViewModel.routeId = routeId

        mobileTicketViewModel.loadMobileTicketData()
        binding.seatingChartBtn.setOnClickListener {
            val routeId = mobileTicketViewModel.routeId
            val action =
                MobileTicketFragmentDirections.actionMobileTicketFragmentToSeatChartFragmentDialogue(
                    routeId
                )
            findNavController().navigate(action)
        }

        mobileTicketViewModel.errMessage.observe(viewLifecycleOwner) {
            showErrorToast(requireContext(), it.toString())
        }


    }

}