package com.softeer.togeduck.ui.home.article_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentArticleDetailBinding
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!


    private val articleDetailViewModel: ArticleDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        articleDetailViewModel.errMessage.observe(viewLifecycleOwner, Observer {
            showErrorToast(requireContext(), it.toString())
        })
    }

    private fun init(){
        binding.vm = articleDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        articleDetailViewModel.getArticleDetails()
    }

}