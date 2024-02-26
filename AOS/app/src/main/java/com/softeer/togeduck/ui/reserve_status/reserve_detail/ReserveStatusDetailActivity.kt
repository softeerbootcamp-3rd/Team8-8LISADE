package com.softeer.togeduck.ui.reserve_status.reserve_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.navArgs
import com.softeer.togeduck.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReserveStatusDetailActivity : AppCompatActivity() {

    private val args: ReserveStatusDetailActivityArgs by navArgs()
    private val reserveStatusDetailViewModel: ReserveStatusDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_status_detail)

        init()
    }

    private fun init() {
        val routeId = args.routeId
        reserveStatusDetailViewModel.routeId = routeId
    }

}