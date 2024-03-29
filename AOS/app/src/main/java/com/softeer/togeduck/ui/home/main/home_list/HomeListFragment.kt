package com.softeer.togeduck.ui.home.main.home_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.home.main.HomeArticleModel
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.databinding.FragmentHomeListBinding
import com.softeer.togeduck.utils.ItemClick
import com.softeer.togeduck.utils.ItemClickWithCategoryId

class HomeListFragment : Fragment() {
    private var _binding: FragmentHomeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: HomeListCategoryChipAdapter
    private lateinit var articleAdapter: HomeListArticleAdapter
    private val homeListViewModel: HomeListViewModel by activityViewModels()

    private val args: HomeListFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        homeListViewModel.apply {
            festivalList.observe(viewLifecycleOwner, Observer {
                setUpRvArticleListRecyclerView(it)
            })
            categoryChipList.observe(viewLifecycleOwner, Observer {
                setUpRvCategoryRecyclerView(it)
            })
        }
//        getArticleSize()
    }


    private fun init() {
        val categoryId = args.categoryId
        setUpArrayAdapter()
        binding.vm = homeListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        homeListViewModel.getCategoryFestival(categoryId)
    }

    private fun getArticleSize() {
        homeListViewModel.setItemSize(articleAdapter.itemCount.toString())
    }

    private fun setUpRvCategoryRecyclerView(data: List<HomeCategoryModel>) {
        val rvCategoryList = binding.rvCategoryList
        categoryAdapter = HomeListCategoryChipAdapter(data, categoryId = args.categoryId-1)
        rvCategoryList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        categoryAdapter.itemClick = object : ItemClickWithCategoryId {
            override fun onClick(view: View, position: Int, categoryId: Int) {
                homeListViewModel.getCategoryFestival(categoryId+1)
            }
        }
    }

    private fun setUpArrayAdapter() {
        val regionArray = resources.getStringArray(R.array.category_sort_list)
        val arrayAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.item_category_sort_list,
                R.id.textView,
                regionArray
            )
        binding.listSortMenu.adapter = arrayAdapter
    }

    private fun setUpRvArticleListRecyclerView(data: List<HomeArticleModel>) {
        articleAdapter = HomeListArticleAdapter(data)
        val rvArticleList = binding.rvArticleList
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        val rvList = binding.rvArticleList
        rvArticleList.addItemDecoration(dividerItemDecoration)
        rvList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }
        articleAdapter.itemClick = object : ItemClick {
            override fun onClick(view: View, position: Int) {
                val articleId = data[position].id
                Log.d("articleID1", articleId.toString())
                val action =
                    HomeListFragmentDirections.actionHomeListFragmentToArticleDetailActivity(
                        articleId
                    )
                findNavController().navigate(action)
            }
        }

    }
}