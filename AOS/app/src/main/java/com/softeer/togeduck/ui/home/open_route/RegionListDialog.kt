package com.softeer.togeduck.ui.home.open_route

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.home.open_route.RegionDetailModel
import com.softeer.togeduck.data.model.home.open_route.RegionListModel
import com.softeer.togeduck.databinding.DialogSelectRegionBinding
import com.softeer.togeduck.utils.ItemClick
import com.softeer.togeduck.utils.ItemClickWithData
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegionListDialog : DialogFragment() {

    private var _binding: DialogSelectRegionBinding? = null
    private val binding get() = _binding!!


    private lateinit var regionListAdapter: RegionListAdapter
    private lateinit var regionDetailListAdapter: RegionDetailListAdapter
    private var selectedRegion = ""
    private var selectedView: View? = null
    private var selectedDetailView: View? = null
    private val regionListViewModel: RegionListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = DialogSelectRegionBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        regionListViewModel.regionList.observe(viewLifecycleOwner, Observer {
            setUpRegionListRecyclerView(it)
        })
        setUpDialogSize()
        selectComplete()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun init(){
        binding.vm = regionListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        regionListViewModel.getPopularFestival()
        regionListViewModel.errMessage.observe(viewLifecycleOwner, Observer {
            showErrorToast(requireContext(), it.toString())
        })
    }
    private fun setUpRegionListRecyclerView(data:List<RegionListModel>) {
        regionListAdapter = RegionListAdapter(data)
        regionDetailListAdapter = RegionDetailListAdapter(emptyList())

        binding.regionList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = regionListAdapter
        }

        binding.placeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = regionDetailListAdapter
        }

        regionListAdapter.itemClick = object : ItemClickWithData {
            override fun onClick(view: View, position: Int, detailList: List<RegionDetailModel>) {
                selectedView?.setBackgroundColor(Color.TRANSPARENT)
                val color = ContextCompat.getColor(requireContext(), R.color.main)
                view.setBackgroundColor(color)
                selectedView = view
                regionDetailListAdapter = RegionDetailListAdapter(detailList).also {
                    binding.placeList.adapter = it
                    it.itemClick = object : ItemClick {
                        override fun onClick(view: View, position: Int) {
                            selectedDetailView?.setBackgroundColor(Color.TRANSPARENT)
                            view.setBackgroundColor(color)
                            selectedDetailView = view
                            selectedRegion = detailList[position].detail
                            regionListViewModel.selectCompleted()
                        }
                    }
                }

            }
        }
        binding.iconClose.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun setUpDialogSize() {
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.9).toInt()
        dialog?.window?.setLayout(
            width,
            height
        )
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialogue_radius
            )
        )
    }

    private fun selectComplete() {
        binding.selectConfirm.setOnClickListener {
            regionListViewModel.setSelectedRegion(selectedRegion)
            regionListViewModel.isSelectedRegionCompleted()
            dialog?.dismiss()
        }

    }

}