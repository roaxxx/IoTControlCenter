package com.jdc.iotcontrolcenter.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.jdc.iotcontrolcenter.IoTControlCenterApplication
import com.jdc.iotcontrolcenter.data.model.RequestLogin
import com.jdc.iotcontrolcenter.databinding.LoginMainBinding
import com.jdc.iotcontrolcenter.domain.Login
import com.jdc.iotcontrolcenter.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : LoginMainBinding
    private val login = Login()
    private lateinit var loadingDialog: AlertDialog
    private val loginViewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog().createProgressDialog(this)

        binding.enterButton.setOnClickListener {
            initLogin()
        }
        loginViewModel.responseLoginModel.observe(this, Observer { loginResponse ->

            Log.i("loginResponse","La respuesta observada $loginResponse")
            if(loginResponse == IoTControlCenterApplication.USER_CREDENTIAL_ERROR){
                SimpleDialog.makeDialog(
                    this,
                    "Error al iniciar sesión",
                    "Usuario i/o contraseña incorrectos"
                )
            }else if (loginResponse == IoTControlCenterApplication.SERVER_CONNECTION_ERROR){
                SimpleDialog.makeDialog(
                    this,
                    "Error de conexión",
                    "Ha ocurrido un error al intentar conectar al servidor, por favor, intentelo más tarde"
                )
            }else{
                intent.putExtra("userName",loginResponse)
                this@LoginActivity.startActivity(intent)
            }
            loadingDialog.hide()
        })
    }

    private fun initLogin() {
        val userEmail = binding.userEmail.text.toString()
        val userPassword = binding.userPassword.text.toString()
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        loadingDialog.show()

        loginViewModel.authenticateOnline(RequestLogin(userEmail,userPassword))
    }

    override fun onResume() {
        super.onResume()
        binding.userEmail.setText("")
        binding.userPassword.setText("")
    }
}