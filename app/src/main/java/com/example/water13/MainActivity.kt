package com.example.water13

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.water13.bean.User
import com.example.water13.component.MyNavigationUI
import com.example.water13.component.toast
import com.example.water13.databinding.ActivityMainBinding
import com.example.water13.login.LoginActivity
import com.example.water13.source.Repo
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupNavigation()
        binding.navigationView.getHeaderView(0).username.text = "用户名:" + User.instance.username
        binding.navigationView.getHeaderView(0).userId.text = "UID:" + User.instance.id.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupNavigation() {
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        MyNavigationUI.setupWithNavController(binding.navigationView, navController) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    Repo.logout(User.instance)
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                } catch (e: Throwable) {
                    toast(e.message)
                }
            }
        }
    }
}
