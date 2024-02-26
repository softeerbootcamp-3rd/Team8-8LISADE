package com.softeer.togeduck.ui.home.open_route

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentOpenRouteBinding


class OpenRouteFragment : Fragment() {
    private var _binding: FragmentOpenRouteBinding? = null
    private val binding get() = _binding!!
    private val regionListViewModel: RegionListViewModel by activityViewModels()

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
        setSelectedImage()
    }

    private fun init() {
        binding.vm = regionListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.leftLayoutView.setOnClickListener {
            regionListViewModel.reSetSelectedCompleted()
            RegionListDialog().show(parentFragmentManager, "ListDialogFragment")
        }
        binding.openRouteButton.setOnClickListener {
            ////////////// 유정-해당 페이지 api 연동하면 수정 필요-
            val routeId = 0
            val action = OpenRouteFragmentDirections.actionOpenRouteFragmentToSeatActivity(routeId)
            findNavController().navigate(action)
            ////////////// 유정-해당 페이지 api 연동하면 수정 필요-
        }
    }

    private fun setSelectedImage(){
        val busImageList = listOf(
            binding.bus14,
            binding.bus25,
            binding.bus45,
        )
        busImageList.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                regionListViewModel.selectBusId(index)
            }
        }
        regionListViewModel.selectedBusId.observe(viewLifecycleOwner, Observer { selectedImageId->
            busImageList.forEachIndexed { index, imageView ->
                if (index == selectedImageId) {
                    imageView.setBackgroundResource(R.drawable.selected_image_border)
                } else {
                    imageView.setBackgroundResource(R.drawable.unselcted_image_border)
                }
            }
            regionListViewModel.isSelectedRegionCompleted()
        })
    }

}