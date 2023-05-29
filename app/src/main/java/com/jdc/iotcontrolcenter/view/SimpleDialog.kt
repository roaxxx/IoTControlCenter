package com.jdc.iotcontrolcenter.view

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.jdc.iotcontrolcenter.R

object SimpleDialog {
    fun makeDialog(context: Context,title: String?, message: String?){
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        message.let { dialog.setMessage(message) }
        dialog.setPositiveButton("ACEPTAR") {alert,_->
            alert.dismiss()
        }
        val alertDialog = dialog.create()
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.light_blue_card_bg)
        alertDialog.show()
    }
}