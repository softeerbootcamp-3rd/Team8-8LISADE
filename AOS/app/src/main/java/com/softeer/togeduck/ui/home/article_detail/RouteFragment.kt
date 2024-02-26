package com.softeer.togeduck.ui.home.article_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.home.article_detail.RouteListModel
import com.softeer.togeduck.databinding.FragmentRouteBinding
import com.softeer.togeduck.utils.ItemClick
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RouteFragment : Fragment() {

    private lateinit var adapter: RouteListAdapter
    private var _binding: FragmentRouteBinding? = null
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
        routeViewModel.getArticleRouteList()
        setUpArrayAdapter()
        makePopUp()
        binding.makeRouteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_routeFragment_to_openRouteActivity)
        }
        routeViewModel.articleRouteList.observe(viewLifecycleOwner, Observer {
            init(it)
        })
        routeViewModel.errMessage.observe(viewLifecycleOwner, Observer {
            showErrorToast(requireContext(), it.toString())
        })

    }

    private fun setUpArrayAdapter() {
        val regionArray = resources.getStringArray(R.array.article_detail_sort_list)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.item_category_sort_list, R.id.textView,regionArray)
        binding.listSortMenu.adapter = arrayAdapter
        binding.vm = routeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun init(data:List<RouteListModel>) {
        adapter = RouteListAdapter(data)
        val rvList = binding.rvRouteList
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.addItemDecoration(dividerItemDecoration)
        adapter.itemClick = object : ItemClick {
            override fun onClick(view: View, position: Int) {
                RouteDetailDialogFragment().show(parentFragmentManager, "RouteDetailDialogFragment")
            }
        }

    }

    private fun makePopUp() {
        binding.selectStartRegion.setOnClickListener {
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
}