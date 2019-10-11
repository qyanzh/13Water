package com.example.water13


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.water13.bean.User
import com.example.water13.component.NETWORK_ERROR_STRING
import com.example.water13.component.toast
import com.example.water13.component.toastUiThread
import com.example.water13.game.GameFragment
import com.example.water13.login.LoginActivity
import com.example.water13.source.Repo
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.bt_start.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Repo.check(User.instance)
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
                    }
                } catch (e: ConnectException) {
                    toastUiThread(NETWORK_ERROR_STRING)
                } catch (e: Exception) {
                    toastUiThread(e.message)
                    Repo.clearUser()
                    startActivity(Intent(context, LoginActivity::class.java))
                    activity?.finish()
                }
            }
        }
        return view
    }


}
