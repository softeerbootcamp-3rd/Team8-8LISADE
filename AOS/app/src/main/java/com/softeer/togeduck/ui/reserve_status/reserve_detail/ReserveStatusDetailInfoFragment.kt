package com.softeer.togeduck.ui.reserve_status.reserve_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentReserveStatusDetailInfoBinding
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReserveStatusDetailInfoFragment : Fragment() {
    private var _binding: FragmentReserveStatusDetailInfoBinding? = null
    private val binding get() = _binding!!
    private val reserveDetailViewModel: ReserveStatusDetailViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reserve_status_detail_info,
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
        binding.vm = reserveDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.checkTicket.setOnClickListener {
            val routeId = reserveDetailViewModel.routeId
            val action = ReserveStatusDetailInfoFragmentDirections.actionReserveStatusDetailInfoFragmentToMobileTicketFragment(routeId)
            findNavController().navigate(action)
        }

        reserveDetailViewModel.loadReserveStatusDetailData()
        reserveDetailViewModel.errMessage.observe(viewLifecycleOwner) {
            showErrorToast(requireContext(), it.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}