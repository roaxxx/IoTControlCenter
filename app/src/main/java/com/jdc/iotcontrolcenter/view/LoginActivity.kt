package com.jdc.iotcontrolcenter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.jdc.iotcontrolcenter.R
import com.jdc.iotcontrolcenter.databinding.LoginMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : LoginMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enterButton.setOnClickListener {
            initLogin()
        }
    }

    private fun initLogin() {
        val userEmail = binding.userEmail.text.toString()
        val userPassword = binding.userPassword.text.toString()
        Toast.makeText(this,"email : $userEmail passw: $userPassword",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,HomeActivity::class.java))
    }
}