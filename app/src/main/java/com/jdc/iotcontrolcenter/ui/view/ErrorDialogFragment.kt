package com.jdc.iotcontrolcenter.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jdc.iotcontrolcenter.R

class ErrorDialogFragment : BottomSheetDialogFragment() {

    private var listener: ErrorDialogListener? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.err_msg_bs,container, false)
        val continueButton = view.findViewById<Button>(R.id.continueBT)

        continueButton.setOnClickListener {
            this.dismiss()
        }

        return view
    }

    // Establece el listener cuando se muestra el diálogo
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ErrorDialogListener) {
            listener = context
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listener = null // Evita posibles fugas de memoria
    }

    override fun dismiss() {
        listener?.onContinueButtonClicked()
        super.dismiss()
    }
}