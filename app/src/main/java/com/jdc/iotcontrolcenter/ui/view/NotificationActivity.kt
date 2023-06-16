package com.jdc.iotcontrolcenter.ui.view



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdc.iotcontrolcenter.data.model.Notification
import com.jdc.iotcontrolcenter.databinding.ActivityNotificationBinding
import com.jdc.iotcontrolcenter.domain.NotificationManager
import com.jdc.iotcontrolcenter.ui.view.adapters.NotificationRecyclerViewAdapter
import kotlinx.coroutines.launch

class NotificationActivity : AppCompatActivity() {

    private val notificationManager = NotificationManager()
    private lateinit var binding :ActivityNotificationBinding
    private lateinit var notificationAdapter: NotificationRecyclerViewAdapter
    private val notificationList = mutableListOf<Notification>()

    private val handler = Handler()
    private val updateInterval = 1000L
    private val dataPointHandler: Runnable = object : Runnable {
        override fun run() {
            showNotifications()
            handler.postDelayed(this, updateInterval)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        binding.deleteNotifications.setOnClickListener {
            lifecycleScope.launch { notificationManager.deleteAllNotifications() }

        }
        showNotifications()
    }

    private fun showNotifications() {
        notificationList.clear()
        lifecycleScope.launch {
            notificationList.addAll(notificationManager.getAllNotifications())
            notificationAdapter.notifyDataSetChanged()
        }
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