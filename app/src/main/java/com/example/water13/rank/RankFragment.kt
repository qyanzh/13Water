package com.example.water13.history.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.water13.component.toast
import com.example.water13.databinding.FragmentRankListBinding

class RankFragment : Fragment() {

    lateinit var binding: FragmentRankListBinding

    private val viewModel: RankViewModel by lazy {
        ViewModelProviders.of(this).get(RankViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        viewModel.message.observe(this, Observer {
            toast(it)
            if(it.isNotEmpty()) {
                viewModel.onMsgShowed()
            }
        })
        val adapter = RankAdapter(context!!)
        binding.rvRankList.adapter = adapter
        binding.rvRankList.itemAnimator = null
        viewModel.rankList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
