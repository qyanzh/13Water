package com.example.water13.rank


import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.water13.R
import com.example.water13.component.toast
import com.example.water13.databinding.FragmentRankListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_query.*
import kotlinx.android.synthetic.main.dialog_query.view.*

class RankFragment : Fragment() {

    lateinit var binding: FragmentRankListBinding

    private val viewModel: RankViewModel by lazy {
        ViewModelProviders.of(this).get(RankViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            if (it.isNotEmpty()) {
                viewModel.onMsgShowed()
            }
        })
        val adapter = RankAdapter(context!!, ItemListener {
            findNavController().navigate(
                RankFragmentDirections.actionRankFragmentToHistoryListFragment(
                    it
                )
            )
        })
        binding.rvRankList.adapter = adapter
        binding.rvRankList.itemAnimator = null
        viewModel.rankList.observe(this, Observer {
            adapter.submitList(it)
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
            .setTitle("请输入玩家Id:")
            .setPositiveButton("查询", null)
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            findNavController().navigate(
                RankFragmentDirections.actionRankFragmentToHistoryListFragment(
                    (dialog.query_dialog.input.et_game_id.text.toString().toInt())
                )
            )
            dialog.dismiss()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.query_gamer_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
