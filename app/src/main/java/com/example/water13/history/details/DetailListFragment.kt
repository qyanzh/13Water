package com.example.water13.history.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.water13.component.toast
import com.example.water13.databinding.FragmentDetailListBinding

class DetailListFragment : Fragment() {

    lateinit var binding: FragmentDetailListBinding

    private val viewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailListBinding.inflate(inflater)
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
        viewModel.getHistoryList(DetailListFragmentArgs.fromBundle(arguments!!).gameId)
        val adapter = DetailAdapter(context!!)
        binding.rvDetailList.adapter = adapter
        binding.rvDetailList.itemAnimator = null
        viewModel.detailsList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
