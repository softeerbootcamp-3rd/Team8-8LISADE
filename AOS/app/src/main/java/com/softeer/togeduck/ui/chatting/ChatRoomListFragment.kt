package com.softeer.togeduck.ui.chatting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.softeer.togeduck.data.model.chatting.ChatRoomListModel
import com.softeer.togeduck.databinding.FragmentChatRoomListBinding
import com.softeer.togeduck.utils.ItemClick
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notifyAll

@AndroidEntryPoint
class ChatRoomListFragment : Fragment() {
    private lateinit var binding: FragmentChatRoomListBinding
    private val chatRoomListViewModel: ChatRoomListViewModel by viewModels()
    private var receiverRegistered = false
    private var receiver : BroadcastReceiver? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun startRegisterReceiver(){
        if(!receiverRegistered){
            if(receiver == null){
                receiver = object: BroadcastReceiver(){
                    override fun onReceive(p0: Context?, p1: Intent?) {
                        chatRoomListViewModel.loadChatRooms()
                    }
                }
            }
            val filter = IntentFilter("com.package.notification")
            requireActivity().registerReceiver(receiver, filter, RECEIVER_EXPORTED)

            receiverRegistered = true
        }
    }

    private fun finishRegisterReceiver() {
        if (receiverRegistered) {
            requireActivity().unregisterReceiver(receiver)
            receiver = null
            receiverRegistered = false
        }
    }

    private fun pauseRegisterReceiver() {
        if (receiverRegistered) {
            receiverRegistered = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        chatRoomListViewModel.loadChatRooms()
        startRegisterReceiver();
    }

    override fun onPause() {
        super.onPause()
        pauseRegisterReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        finishRegisterReceiver()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        startRegisterReceiver()
    }

    private fun initObservers() {
        chatRoomListViewModel.chatRooms.observe(viewLifecycleOwner) { chatRooms ->
            val adapter = ChatRoomListAdapter(chatRooms)
            binding.rvChatRoomList.adapter = adapter

            adapter.itemClick = object : ItemClick {
                override fun onClick(view: View, position: Int) {
                    val chatRoom = chatRooms[position]
                    val intent = Intent(requireContext(), ChatRoomActivity::class.java).apply {
                        putExtra("id", chatRoom.id)
                        putExtra("roomName", chatRoom.roomName)
                    }

                    setUnreadMessageCountZero(chatRoom)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setUnreadMessageCountZero(chatRoomListModel: ChatRoomListModel) {
        chatRoomListViewModel.updateChatRoom(chatRoomListModel.copy(unreadMessageCount = 0))
    }
}