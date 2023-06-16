package com.jdc.iotcontrolcenter.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.jdc.iotcontrolcenter.IoTControlCenterApplication
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.databinding.LoginMainBinding
import com.jdc.iotcontrolcenter.domain.Login
import kotlinx.coroutines.launch

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
            val loginResponse = login.invoke(RequestLogin(userEmail,userPassword))
            Log.i("token","token: $loginResponse")

            if(loginResponse == IoTControlCenterApplication.USER_CREDENTIAL_ERROR){
                SimpleDialog.makeDialog(
                    this@LoginActivity,
                    "Error al iniciar sesión",
                    "Usuario i/o contraseña incorrectos")
            }else if (loginResponse == IoTControlCenterApplication.SERVER_CONNECTION_ERROR){
                SimpleDialog.makeDialog(
                    this@LoginActivity,
                    "Error de conexión",
                    "Ha ocurrido un error al intentar conectar al servidor, por favor, intentelo más tarde")
            }else{
                intent.putExtra("userName",loginResponse)
                this@LoginActivity.startActivity(intent)
            }
            loadingDialog.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.userEmail.setText("")
        binding.userPassword.setText("")
    }
}