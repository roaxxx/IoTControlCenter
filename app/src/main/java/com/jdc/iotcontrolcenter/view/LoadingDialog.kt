package com.jdc.iotcontrolcenter.view

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.jdc.iotcontrolcenter.R

class LoadingDialog {

    fun createProgressDialog(context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context)
        val view = (context as Activity).layoutInflater.inflate(R.layout.loading_alert_dialog, null)
        builder.setView(view)
        val loadingDialog = builder.create()
        loadingDialog.window?.setBackgroundDrawableResource(R.drawable.light_blue_card_bg)
        return loadingDialog
    }
}