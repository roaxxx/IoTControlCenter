package com.jdc.iotcontrolcenter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.databinding.LoginMainBinding
import com.jdc.iotcontrolcenter.domain.Login
import kotlinx.coroutines.launch
import okio.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : LoginMainBinding
    private val login = Login()
    private lateinit var loadingDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog().createProgressDialog(this)


        binding.enterButton.setOnClickListener {
            initLogin()
        }
    }

    private fun initLogin() {
        val userEmail = binding.userEmail.text.toString()
        val userPassword = binding.userPassword.text.toString()
        lifecycleScope.launch{

            val intent = Intent(this@LoginActivity,HomeActivity::class.java)
            loadingDialog.show()
            val userName = login.invoke(RequestLogin(userEmail,userPassword))
            if(userName!=null){
                intent.putExtra("userName",userName)
                this@LoginActivity.startActivity(intent)
            }else{
                SimpleDialog.makeDialog(
                    this@LoginActivity,
                    "Error al iniciar sesión",
                    "Usuario i/o contraseña incorrectos")
            }
            loadingDialog.hide()
        }

    }
}