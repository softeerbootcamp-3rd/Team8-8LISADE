package com.softeer.togeduck.ui.article_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityArticleDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail)
        setUpNavigator()
    }

    private fun setUpNavigator(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.detailFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.routeButton.setOnClickListener{
            navController.navigate(R.id.action_articleDetailFragment_to_routeFragment)
            binding.routeButton.isEnabled = false
            binding.articleDetailButton.isEnabled = true
        }

        binding.articleDetailButton.setOnClickListener{
            navController.navigate(R.id.action_routeFragment_to_articleDetailFragment)
            binding.routeButton.isEnabled = true
            binding.articleDetailButton.isEnabled = false
        }
    }
}