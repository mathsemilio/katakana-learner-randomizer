package com.mathsemilio.katakanalearner.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.databinding.MainGameScreenBinding
import com.mathsemilio.katakanalearner.ui.viewModel.MainGameScreenViewModel

private const val TAG_MAIN_GAME_SCREEN = "MainGameScreen"

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

        binding.radioGroupKatakanaLetters.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == -1) {
                binding.buttonVerifyAnswer.isEnabled = false
            } else {
                binding.buttonVerifyAnswer.isEnabled = true

                val radioButton: RadioButton = group.findViewById(checkedId)
                val selectedRomanization = radioButton.text.toString()

                binding.buttonVerifyAnswer.setOnClickListener {
                    if (viewModel.katakanaLettersList.size == 1) {
                        viewModel.getLastLetter(selectedRomanization)
                    } else {
                        viewModel.checkUserInput(selectedRomanization)
                    }
                }
            }
        }

        binding.buttonExit.setOnClickListener { navigateToWelcomeScreen() }

        viewModel.katakanaLetterDrawableId.observe(
            viewLifecycleOwner,
            Observer { letterDrawableId ->
                binding.imageKatakanaLetterMainGameScreen.setImageResource(letterDrawableId)
            })

        viewModel.eventCorrectAnswer.observe(viewLifecycleOwner, Observer { answerIsCorrect ->
            if (answerIsCorrect == true) {
                buildAlertDialog(
                    R.string.dialog_correct_answer_title,
                    getString(R.string.dialog_correct_answer_message),
                    R.string.dialog_correct_answer_positive_button,
                    DialogInterface.OnClickListener { _, _ ->
                        if (viewModel.eventGameFinished.value == true) {
                            navigateToScoreScreen(viewModel.gameScore.value!!.toInt())
                        } else {
                            viewModel.getNextLetter()
                            binding.radioGroupKatakanaLetters.clearCheck()
                        }
                    }
                )
            } else {
                buildAlertDialog(
                    R.string.dialog_incorrect_answer_title,
                    getString(
                        R.string.dialog_incorrect_answer_message,
                        viewModel.currentKatakanaLetterRomanization.value
                    ),
                    R.string.dialog_incorrect_answer_positive_button,
                    DialogInterface.OnClickListener { _, _ ->
                        if (viewModel.eventGameFinished.value == true) {
                            navigateToScoreScreen(viewModel.gameScore.value!!.toInt())
                        } else {
                            viewModel.getNextLetter()
                            binding.radioGroupKatakanaLetters.clearCheck()
                        }
                    }
                )
            }
        })

        return binding.root
    }

    private fun navigateToWelcomeScreen() {
        activity?.findNavController(R.id.nav_host_fragment)
            ?.navigate(R.id.action_mainGameScreen_to_gameWelcomeScreen)
    }

    private fun navigateToScoreScreen(gameScore: Int) {
        val action = MainGameScreenDirections.actionMainGameScreenToGameScoreScreen(gameScore)
        activity?.findNavController(R.id.nav_host_fragment)?.navigate(action)
    }

    private fun buildAlertDialog(
        title: Int, message: String, positiveButtonText: Int,
        listener: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(activity).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveButtonText, listener)
            setCancelable(false)
            show()
        }
    }
}