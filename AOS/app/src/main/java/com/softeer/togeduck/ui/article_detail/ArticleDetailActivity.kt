package com.softeer.togeduck.ui.article_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.ActivityArticleDetailBinding
import java.security.AccessController.getContext




class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail)
        init()
    }

    private fun init() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.detailFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val defaultColor = ContextCompat.getColor(this@ArticleDetailActivity, R.color.gray300)
        val checkedColor = ContextCompat.getColor(this@ArticleDetailActivity, R.color.main)
        val transparentColor = ContextCompat.getColor(this@ArticleDetailActivity, R.color.transparent)

        binding.routeButton.setOnClickListener {
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