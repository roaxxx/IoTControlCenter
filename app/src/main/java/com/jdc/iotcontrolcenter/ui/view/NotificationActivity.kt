package com.jdc.iotcontrolcenter.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdc.iotcontrolcenter.data.model.Notification
import com.jdc.iotcontrolcenter.databinding.ActivityNotificationBinding
import com.jdc.iotcontrolcenter.ui.view.adapters.NotificationRecyclerViewAdapter
import com.jdc.iotcontrolcenter.ui.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {

    private val notificationViewModel :NotificationViewModel by viewModels()
    private lateinit var binding :ActivityNotificationBinding
    private lateinit var notificationAdapter: NotificationRecyclerViewAdapter
    private val notificationList = mutableListOf<Notification>()

    private val handler = Handler()
    private val updateInterval = 1000L
    private val dataPointHandler: Runnable = object : Runnable {
        override fun run() {
            notificationViewModel.getAllNotifications()
            handler.postDelayed(this, updateInterval)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        binding.deleteNotifications.setOnClickListener {
            notificationViewModel.deleteAllNotifications()

        }
        showNotifications()
        showNotifications()
        notificationViewModel.getAllNotifications()
    }

    private fun showNotifications() {
        notificationViewModel.noticationLisObservable.observe(this, Observer { notifications ->
            notificationList.clear()
            notificationList.addAll(notifications)
            notificationAdapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        notificationAdapter = NotificationRecyclerViewAdapter(notificationList)
        binding.notificationList.layoutManager = LinearLayoutManager(this)
        binding.notificationList.adapter = notificationAdapter
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(dataPointHandler, updateInterval)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(dataPointHandler)
    }
}