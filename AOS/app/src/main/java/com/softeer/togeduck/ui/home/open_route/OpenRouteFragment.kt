package com.softeer.togeduck.ui.home.open_route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentOpenRouteBinding


class OpenRouteFragment : Fragment() {
    private var _binding: FragmentOpenRouteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpenRouteBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.leftLayoutView.setOnClickListener {
            RegionListDialog().show(parentFragmentManager, "ListDialogFragment")
        }
        binding.openRouteButton.setOnClickListener{
            findNavController().navigate(R.id.action_openRouteFragment_to_seatActivity)
        }
    }
}