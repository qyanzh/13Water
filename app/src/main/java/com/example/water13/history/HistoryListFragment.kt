package com.example.water13.history


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.water13.component.toast
import com.example.water13.databinding.FragmentHistoryListBinding

class HistoryListFragment : Fragment() {

    lateinit var binding: FragmentHistoryListBinding

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProviders.of(this).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryListBinding.inflate(inflater)
        binding.model = viewModel
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
        viewModel.isRefreshing.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = it
        })
        binding.swipeRefresh.let {
            it.setOnRefreshListener {
                viewModel.getHistoryList(true)
            }
        }
        val adapter = HistoryAdapter(context!!, ItemListener {
            findNavController().navigate(
                HistoryListFragmentDirections.actionHistoryListFragmentToDetailListFragment(
                    it.id
                )
            )
        })
        binding.rvHistoryList.adapter = adapter
        binding.rvHistoryList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if ((recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() + 1 == recyclerView.adapter?.itemCount) {
                        Log.d("TAG", "到底了")
                        viewModel.getHistoryList()
                        toast("加载中")
                    }
                }
            }
        })
        viewModel.historyList.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }


}
