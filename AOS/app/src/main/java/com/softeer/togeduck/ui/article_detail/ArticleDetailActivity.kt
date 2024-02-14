package com.softeer.togeduck.ui.article_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityArticleDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail)
    }

    private fun setUpNavigator(){
        binding.routeButton.setOnClickListener{

        }
        binding.articleDetailButton.setOnClickListener{

        }
    }
}