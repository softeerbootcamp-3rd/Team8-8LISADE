package com.softeer.togeduck.ui.home.article_detail

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.ActivityArticleDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding
    private val args: ArticleDetailActivityArgs by navArgs()
    private val articleDetailViewModel: ArticleDetailViewModel by viewModels()
    private val routeViewModel: RouteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail)
        init()

    }

    private fun init() {
        val articleId = args.articleId


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.detailFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val defaultColor = ContextCompat.getColor(this@ArticleDetailActivity, R.color.gray300)
        val checkedColor = ContextCompat.getColor(this@ArticleDetailActivity, R.color.main)
        val transparentColor = ContextCompat.getColor(this@ArticleDetailActivity, R.color.transparent)

        binding.routeButton.setOnClickListener {
            routeViewModel.getArticleId(articleId)
            navController.navigate(R.id.action_articleDetailFragment_to_routeFragment)
            binding.run{
                routeButton.setTextColor(checkedColor)
                articleDetailButton.setTextColor(defaultColor)
                articleDetailIndicator.setBackgroundColor(transparentColor)
                routeIndicator.setBackgroundColor(checkedColor)
                routeButton.isEnabled = false
                articleDetailButton.isEnabled = true
            }
        }

        binding.articleDetailButton.setOnClickListener {
            articleDetailViewModel.getArticleId(articleId)
            navController.navigate(R.id.action_routeFragment_to_articleDetailFragment)
            binding.run{
                routeButton.setTextColor(defaultColor)
                articleDetailButton.setTextColor(checkedColor)
                articleDetailIndicator.setBackgroundColor(checkedColor)
                routeIndicator.setBackgroundColor(transparentColor)
                routeButton.isEnabled = true
                articleDetailButton.isEnabled = false
            }
        }
    }
}