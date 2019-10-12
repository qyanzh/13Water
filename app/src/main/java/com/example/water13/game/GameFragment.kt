package com.example.water13.game


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.water13.component.loadPoker
import com.example.water13.component.toCardImages
import com.example.water13.component.toast
import com.example.water13.databinding.FragmentGameBinding
import kotlinx.android.synthetic.main.cards_vertical.*


class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    val viewList: MutableList<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            add(card0)
            add(card1)
            add(card2)
            add(card3)
            add(card4)
            add(card5)
            add(card6)
            add(card7)
            add(card8)
            add(card9)
            add(card10)
            add(card11)
            add(card12)
        }
    }

    val viewModel: GameViewModel by lazy {
        ViewModelProviders.of(activity!!).get(GameViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.model = viewModel
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        viewModel.onNextClicked()
        binding.btNext.setOnClickListener {
            viewModel.onNextClicked()
        }
        viewModel.cardsString.observe(this, Observer {
            loadPoker(context!!, it.toCardImages(), viewList)
        })
        viewModel.message.observe(this, Observer {
            toast(it)
            if(it.isNotEmpty()) {
                viewModel.onMsgShowed()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.closeAuto()
    }
}
