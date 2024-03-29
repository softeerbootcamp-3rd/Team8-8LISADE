package com.softeer.togeduck.ui.home.article_detail


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentRouteDetailBinding


class RouteDetailDialogFragment : DialogFragment() {
    private var _binding: FragmentRouteDetailBinding? = null
    private val binding get() = _binding!!
    private val args: RouteDetailDialogFragmentArgs by navArgs()
    private val routeDetailDialogViewModel: RouteDetailDialogViewModel by activityViewModels()
    private lateinit var mapView: MapView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRouteDetailBinding.inflate(inflater, null, false)
        return binding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.9).toInt()
        dialog?.window?.setLayout(
            width,
            height
        )
        init()
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialogue_radius
            )
        )
        // 수정필요
//        initMap()


    }

    private fun init() {
        binding.vm = routeDetailDialogViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        routeDetailDialogViewModel.getRouteDetails()
        binding.joinButton.setOnClickListener {
            val action =
                RouteDetailDialogFragmentDirections.actionRouteDetailDialogFragmentToSeatActivity2(args.routeId)
            findNavController().navigate(action)
        }

        binding.iconClose.setOnClickListener {
            dialog?.dismiss()
        }


    }


    private fun initMap() {
        mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
                Log.d("TESTLOG1", "SUCCESS")
            }

            override fun onMapError(error: Exception) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                Log.d("TESTLOG2", error.toString())
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                // 인증 후 API 가 정상적으로 실행될 때 호출됨

                Log.d("TESTLOG3", kakaoMap.toString())
            }
        })
    }

}