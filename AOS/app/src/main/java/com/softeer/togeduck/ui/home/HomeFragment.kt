package com.softeer.togeduck.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.data.model.PopularArticleModel
import com.softeer.togeduck.databinding.FragmentHomeBinding

private val dummyData = listOf(
    PopularArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "2024.02.07", "잠실종합운동장"),
    PopularArticleModel("dummy", "[부산] 싸이 흠뻑쇼", "2024.02.14", "부산시청"),
    PopularArticleModel("dummy", "[부산] 싸이 흠뻑쇼", "2024.02.14", "부산시청"),
    PopularArticleModel("dummy", "[부산] 싸이 흠뻑쇼", "2024.02.14", "부산시청"),
    PopularArticleModel("dummy", "[부산] 싸이 흠뻑쇼", "2024.02.14", "부산시청"),
    PopularArticleModel("dummy", "[부산] 싸이 흠뻑쇼", "2024.02.14", "부산시청")
)

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.rvItemPopularArticle.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = PopularArticleAdapter(dummyData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}