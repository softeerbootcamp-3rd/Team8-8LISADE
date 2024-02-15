package com.softeer.togeduck.ui.article_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.PopularArticleModel
import com.softeer.togeduck.data.model.RouteListModel
import com.softeer.togeduck.databinding.FragmentHomeBinding
import com.softeer.togeduck.databinding.FragmentRouteBinding



//data class RouteListModel(
//    val startDate: String,
//    val place: String,
//    val price: String,
//    val totalPeople: Int,
//    val currentPeople: Int,
//    val currentType: String,
//)

private val dummyData = listOf(
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
)
class RouteFragment : Fragment() {
    private var _binding: FragmentRouteBinding? =null
    private val binding get() = _binding!!
    private val routeViewModel: RouteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRouteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpArrayAdapter()
        init()
        makePopUp()
    }
    private fun setUpArrayAdapter() {
        val regionArray = resources.getStringArray(R.array.article_detail_sort_list)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.item_category_sort_list, regionArray)
        binding.listSortMenu.adapter = arrayAdapter
        binding.vm = routeViewModel
    }

    private fun init() {
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvRouteList.addItemDecoration(dividerItemDecoration)
        binding.rvRouteList.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = RouteListAdapter(dummyData)
        }
    }
    private fun makePopUp(){
        binding.selectStartRegion.setOnClickListener{
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.start_region_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                }
                true
            }
            popup.show()
            true
        }
    }

//    private fun getArticleSize(){
//        routeViewModel.getItemSize(articleAdapter.itemCount.toString())
//    }

}