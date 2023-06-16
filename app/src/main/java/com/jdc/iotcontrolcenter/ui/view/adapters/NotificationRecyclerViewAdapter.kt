package com.jdc.iotcontrolcenter.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdc.iotcontrolcenter.R
import com.jdc.iotcontrolcenter.data.model.Notification
import com.jdc.iotcontrolcenter.databinding.NotificationItemBinding

class NotificationRecyclerViewAdapter(
val notifications: MutableList<Notification>
) : RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = NotificationItemBinding.bind(itemView)

        fun render(notification: Notification){
            binding.notificationDate.text = notification.notificationDate+", "+notification.notificationTime
            if (notification.alarmId=="alarm01"){
                binding.notificationMessage.text = "Se ha detectado un intruso recientemente"
            }else{
                binding.notificationMessage.text = "Se ha llegado a la temperatura maxima estableciad de "+
                        notification.trigger+"°C"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(notifications[position])
    }
}