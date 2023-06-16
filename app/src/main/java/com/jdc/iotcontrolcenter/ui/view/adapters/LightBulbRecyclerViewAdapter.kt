package com.jdc.iotcontrolcenter.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdc.iotcontrolcenter.R
import com.jdc.iotcontrolcenter.data.model.Lightbulb
import com.jdc.iotcontrolcenter.databinding.LightbulbItemBinding

class LightBulbRecyclerViewAdapter(
    val lightbulbs: MutableList<Lightbulb>,
    private val onClickChangeState: (Int)->Unit
) : RecyclerView.Adapter<LightBulbRecyclerViewAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lightbulb_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(lightbulbs[position],onClickChangeState)
    }

    override fun getItemCount(): Int = lightbulbs.size


    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = LightbulbItemBinding.bind(itemView)

        fun render(lightbulb: Lightbulb, onClickChangeState: (Int) -> Unit){
            if(lightbulb.bulbValue=="1"){
                binding.lightbulbImg.setImageResource(R.drawable.ic_lightbulb_on)
                binding.changeLightBulbState.text = "Apagar"
            }else if (lightbulb.bulbValue.length>1){
                binding.changeLightBulbState.text = "Cambiar color"
            }else{
                binding.lightbulbImg.setImageResource(R.drawable.ic_lightbulb_off)
                binding.changeLightBulbState.text = "Prender"
            }

            binding.lightbulbUbication.text = lightbulb.ubication

            binding.changeLightBulbState.setOnClickListener {
                onClickChangeState(bindingAdapterPosition)
            }
        }
    }
}