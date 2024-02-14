package com.softeer.togeduck.ui.home.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.HomeArticleModel
import com.softeer.togeduck.data.model.HomeCategoryModel
import com.softeer.togeduck.databinding.FragmentHomeListBinding
import com.softeer.togeduck.utils.ItemClick


private val dummyData = listOf(
    HomeCategoryModel("dummy", "스포츠"),
    HomeCategoryModel("dummy", "뮤지컬"),
    HomeCategoryModel("dummy", "콘서트"),
    HomeCategoryModel("dummy", "팬미팅"),
    HomeCategoryModel("dummy", "애니메이션"),
    HomeCategoryModel("dummy", "기타"),
)

private val articleDummyData = listOf(
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02")
)

class HomeListFragment : Fragment() {
    private var _binding: FragmentHomeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: HomeListCategoryChipAdapter
    private lateinit var articleAdapter: HomeListArticleAdapter
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
        setUpArrayAdapter()
        init()
    }

    private fun setUpArrayAdapter() {
        val regionArray = resources.getStringArray(R.array.category_sort_list)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.item_category_sort_list, regionArray)
        binding.listSortMenu.adapter = arrayAdapter
    }

    private fun init() {
        val rvCategoryList = binding.rvCategoryList
        val rvArticleList = binding.rvArticleList
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rvArticleList.addItemDecoration(dividerItemDecoration)
        categoryAdapter = HomeListCategoryChipAdapter(dummyData)
        articleAdapter = HomeListArticleAdapter(articleDummyData)
        rvCategoryList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter

        }
        rvArticleList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }

        categoryAdapter.itemClick = object : ItemClick {
            override fun onClick(view: View, position: Int) {
                Log.d("TESTLOG", "카테고리 클릭")

            }
        }
        articleAdapter.itemClick = object : ItemClick {
            override fun onClick(view: View, position: Int) {
                findNavController().navigate(R.id.action_homeListFragment_to_articleDetailActivity)
            }
        }
    }
}