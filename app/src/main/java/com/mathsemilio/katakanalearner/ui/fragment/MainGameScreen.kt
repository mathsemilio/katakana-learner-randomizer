package com.mathsemilio.katakanalearner.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.databinding.MainGameScreenBinding
import com.mathsemilio.katakanalearner.ui.viewModel.MainGameScreenViewModel

class MainGameScreen : Fragment() {

    private lateinit var viewModel: MainGameScreenViewModel
    private lateinit var binding: MainGameScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_game_screen, container, false)

        viewModel = ViewModelProvider(this).get(MainGameScreenViewModel::class.java)

        binding.mainGameViewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.katakanaLetterDrawableId.observe(
            viewLifecycleOwner,
            Observer { letterDrawableId ->
                binding.imageKatakanaLetterMainGameScreen.setImageResource(letterDrawableId)
            })

        return binding.root
    }

}