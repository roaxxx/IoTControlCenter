package com.jdc.iotcontrolcenter.view

import android.content.Context
import androidx.appcompat.app.AlertDialog

object SimpleDialog {
    fun makeDialog(context: Context,title: String?, message: String?){
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        message.let { dialog.setMessage(message) }
        dialog.setPositiveButton("ACEPTAR") {alert,_->
            alert.dismiss()
        }
        val alertDialog = dialog.create()
        alertDialog.show()
    }
}