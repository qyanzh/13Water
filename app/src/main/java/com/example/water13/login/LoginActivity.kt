package com.example.water13.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.water13.*
import com.example.water13.bean.User
import com.example.water13.component.toast
import com.example.water13.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(User.instance.username!="") {
            navigateToMain()
        } else {
            binding = DataBindingUtil.setContentView(this, R.layout.login_activity)
            binding.model = viewModel
            subscribeUi()
        }
    }

    private fun subscribeUi() {
        binding.btLogin.setOnClickListener {
            viewModel.onLoginClicked()
        }
        binding.btRegister.setOnClickListener {
            viewModel.onRegisterClicked()
        }
        viewModel.message.observe(this, Observer {
            toast(it)
        })
        viewModel.success.observe(this, Observer { success ->
            if (success) {
                navigateToMain()
            }
        })
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
