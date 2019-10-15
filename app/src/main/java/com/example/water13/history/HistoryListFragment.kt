package com.example.water13.history


import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.water13.R
import com.example.water13.component.toast
import com.example.water13.databinding.FragmentHistoryListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_query.*
import kotlinx.android.synthetic.main.dialog_query.view.*

class HistoryListFragment : Fragment() {

    lateinit var binding: FragmentHistoryListBinding

    private val viewModel: HistoryViewModel by lazy {
        var gamerId = 0
        try {
            gamerId = HistoryListFragmentArgs.fromBundle(arguments!!).gamerId
            toast("正在查看玩家${gamerId}的历史战绩")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        ViewModelProviders.of(
            this, HistoryViewModel.Factory(
                gamerId
            )
        ).get(HistoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            if (it.isNotEmpty()) {
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
                        viewModel.getHistoryList(false)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                showQueryDialog()
                return true
            }
        }
        return false
    }

    private fun showQueryDialog() {
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
            .setView(R.layout.dialog_query)
            .setTitle("请输入场次Id:")
            .setPositiveButton("查询", null)
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            findNavController().navigate(
                HistoryListFragmentDirections.actionHistoryListFragmentToDetailListFragment(
                    dialog.query_dialog.input.et_game_id.text.toString().toInt()
                )
            )
            dialog.dismiss()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.query_game_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}
