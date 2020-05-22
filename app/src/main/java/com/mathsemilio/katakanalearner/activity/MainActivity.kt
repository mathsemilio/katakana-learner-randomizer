package com.mathsemilio.katakanalearner.activity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mathsemilio.katakanalearner.R
import com.mathsemilio.katakanalearner.databinding.ActivityMainBinding
import com.mathsemilio.katakanalearner.util.SharedPreferencesGameScore
import com.mathsemilio.katakanalearner.util.TAG_MAIN_ACTIVITY

class MainActivity : AppCompatActivity() {

    //==========================================================================================
    // Companion Object
    //==========================================================================================
    companion object {
        // String keys for the onSaveInstanceState
        private const val SAVED_SYMBOL_ID = "savedSymbolId"
        private const val SAVED_ROMANIZATION = "savedRomanization"
        private const val SAVED_GAME_SCORE = "savedGameScore"

        // String key for SharedPreferences
        private const val GAME_SCORE_KEY = "gameScoreKey"
    }

    //==========================================================================================
    // Data Class
    //==========================================================================================

    // Data class for representing the Katakana Symbols
    data class KatakanaSymbol(
        val imageSymbolId: Int,
        val romanization: String
    )

    //==========================================================================================
    // Katakana Symbols List
    //==========================================================================================
    private val katakanaSymbol: MutableList<KatakanaSymbol> = mutableListOf(
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col1_a,
            romanization = "A"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col1_i,
            romanization = "I"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col1_u,
            romanization = "U"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col1_e,
            romanization = "E"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col1_o,
            romanization = "O"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col2_ka,
            romanization = "KA"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col2_ki,
            romanization = "KI"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col2_ku,
            romanization = "KU"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col2_ke,
            romanization = "KE"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col2_ko,
            romanization = "KO"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col3_sa,
            romanization = "SA"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col3_shi,
            romanization = "SHI"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col3_su,
            romanization = "SU"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col3_se,
            romanization = "SE"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col3_so,
            romanization = "SO"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col4_ta,
            romanization = "TA"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col4_chi,
            romanization = "CHI"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col4_tsu,
            romanization = "TSU"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col4_tse,
            romanization = "TSE"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col4_to,
            romanization = "TO"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col5_na,
            romanization = "NA"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col5_ni,
            romanization = "NI"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col5_nu,
            romanization = "NU"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col5_ne,
            romanization = "NE"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col5_no,
            romanization = "NO"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col6_ha,
            romanization = "HA"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col6_hi,
            romanization = "HI"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col6_fu,
            romanization = "FU"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col6_he,
            romanization = "HE"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col6_ho,
            romanization = "HO"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col7_ma,
            romanization = "MA"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col7_mi,
            romanization = "MI"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col7_mu,
            romanization = "MU"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col7_me,
            romanization = "ME"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col7_mo,
            romanization = "MO"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col8_ya,
            romanization = "YA"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col8_yu,
            romanization = "YU"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col8_yo,
            romanization = "YO"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col9_ra,
            romanization = "RA"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col9_ri,
            romanization = "RI"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col9_ru,
            romanization = "RU"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col9_re,
            romanization = "RE"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col9_ro,
            romanization = "RO"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col10_wa,
            romanization = "WA"
        ),
        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col10_wo,
            romanization = "WO"
        ),

        KatakanaSymbol(
            imageSymbolId = R.drawable.ic_col11_n,
            romanization = "N"
        )
    )

    //==========================================================================================
    // Class-wide variables
    //==========================================================================================

    // Variable for the binding class
    private lateinit var binding: ActivityMainBinding

    // Variable for the Game Score
    private var gameScore: Int = 0

    // Variable which implements a lambda function that will call shuffleList().
    private val funShuffleList = { _: DialogInterface, _: Int ->
        shuffleList()
    }

    //==========================================================================================
    // OnCreate
    //==========================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.i(TAG_MAIN_ACTIVITY, "Setting up the Toolbar")
        // Calling the function that sets up the Toolbar
        setUpActivityToolbar()

        // Setting up a OnClickListener for the buttonGerarSimbolo Button
        binding.buttonGerarSimbolo.setOnClickListener {
            shuffleList()
            binding.radioGroupKatakanaOptions.clearCheck()
        }

        /*
        Checking if the savedInstanceState is null (which it is if the activity has not been
        destroyed before), if it is we call the shuffleList function and the game flows from there.
        If it isn't, we call both the handleVectorDisplayAndRadioButtonRandomization and the
        handleUserInteraction function, and pass the parameters recovered from the savedInstanceState.
         */
        if (savedInstanceState == null) {
            Log.i(TAG_MAIN_ACTIVITY, "Calling the shuffleList function")
            shuffleList()
            Log.i(
                TAG_MAIN_ACTIVITY, "Setting the textScoreNumber TextView value as the " +
                        "value of the gameScore variable"
            )
            binding.textScoreNumber.text = gameScore.toString()
        } else {
            Log.i(
                TAG_MAIN_ACTIVITY, "Calling handleVectorDisplayAndRadioButtonRomanization " +
                        "and passing the symbol ID and the romanization saved from the " +
                        "onSaveInstanceState function"
            )
            savedInstanceState.getString(SAVED_ROMANIZATION)?.let {
                handleVectorDisplayAndRadioButtonRandomization(
                    savedInstanceState.getInt(SAVED_SYMBOL_ID), it
                )
            }

            Log.i(
                TAG_MAIN_ACTIVITY, "Calling handleUserInteraction and passing the " +
                        "romanization saved on the onSaveInstanceState function."
            )
            savedInstanceState.getString(SAVED_ROMANIZATION)?.let { handleUserInteraction(it) }

            Log.i(TAG_MAIN_ACTIVITY, "Restoring the previous game score")
            gameScore = savedInstanceState.getInt(SAVED_GAME_SCORE)
            binding.textScoreNumber.text = gameScore.toString()
        }
    }

    //==========================================================================================
    // ShuffleList function
    //==========================================================================================

    /**
     * Void function that shuffles the list of Katakana Symbols, and passes the first
     * imageSymbolId and romanization to the handleVectorDisplayAndRadioButtonRandomization. It
     * also calls the handleUserInteraction function and passes the same romanization.
     * @author mathsemilio
     */
    private fun shuffleList() {
        // Shuffling the list containing all the Katakana symbols
        katakanaSymbol.shuffle()

        /*
        Calling the handleVectorDisplayAndRadioButtonRandomization function and passing it's required
        parameters, a imageSymbolId and a string containing the correct romanization.
         */
        handleVectorDisplayAndRadioButtonRandomization(
            katakanaSymbol.first().imageSymbolId,
            katakanaSymbol.first().romanization
        )

        /*
        Calling the handleUserInteraction function and passing the required parameter, a string
        containing the correct romanization.
         */
        handleUserInteraction(katakanaSymbol.first().romanization)
    }

    //==========================================================================================
    // HandleVectorDisplayAndRadioButtonRandomization function
    //==========================================================================================

    /**
     * Void function that handles the randomization of the text on the RadioButtons, it also
     * handles the display of the katakana symbol on the screen and which radio button will receive
     * the correct romanization for the user to select.
     * @param imageSymbolId: Int - Contains the ID of a katakana symbol vector.
     * @param correctRomanization: String - Contains the string of a romanization corresponding to
     * a particular katakana symbol.
     * @author mathsemilio
     */
    private fun handleVectorDisplayAndRadioButtonRandomization(
        imageSymbolId: Int,
        correctRomanization: String
    ) {
        // Setting the vector image to be displayed in the screen
        binding.imageKatakanaSymbol.setImageResource(imageSymbolId)

        // A list containing strings to be used as incorrect romanizations for the radio buttons
        val randomSymbolsList = listOf(
            "A", "E", "O", "KA", "KI", "KU", "SHI", "SE", "SO", "CHI", "TSU", "TSE", "NI", "NU",
            "NO", "HA", "FU", "HO", "MI", "ME", "MU", "RI", "RU", "RO", "N"
        )

        // Shuffling the list to change the order of its elements
        randomSymbolsList.shuffled()

        /*
        Setting the text for the Radio Buttons. We are giving each of the radio buttons the first
        string romanization sliced from the list. The first{} function was used to make sure none
        of the buttons had the correctRomanization before the we sort which of them would receive
        the string containing the correct romanization. From the 2nd to the 4th button, it was also
        added to the boolean expression, the condition that the string sliced from the list would
        not be equal to the text from button above (eg. the text from the 2nd radio button will not
        be equal to text from the 1st one.)
         */
        binding.radioButtonOption1.text = randomSymbolsList.slice(0..5).first {
            it != correctRomanization
        }

        binding.radioButtonOption2.text = randomSymbolsList.slice(6..11).first {
            it != correctRomanization && it != binding.radioButtonOption1.text
        }

        binding.radioButtonOption3.text = randomSymbolsList.slice(12..18).first {
            it != correctRomanization && it != binding.radioButtonOption2.text
        }

        binding.radioButtonOption4.text = randomSymbolsList.slice(19..24).first {
            it != correctRomanization && it != binding.radioButtonOption3.text
        }

        /*
        Simple algorithm to generate a random number between 0 and 4, and based on that number, we
        select which radio button will receive the string containing the correct romanization for
        the symbol on the screen.
         */
        when ((0 until 4).random()) {
            0 -> {
                Log.i(TAG_MAIN_ACTIVITY, "rBtn1 Selected")
                binding.radioButtonOption1.text = correctRomanization
            }
            1 -> {
                Log.i(TAG_MAIN_ACTIVITY, "rBtn2 Selected")
                binding.radioButtonOption2.text = correctRomanization
            }
            2 -> {
                Log.i(TAG_MAIN_ACTIVITY, "rBtn3 Selected")
                binding.radioButtonOption3.text = correctRomanization
            }
            3 -> {
                Log.i(TAG_MAIN_ACTIVITY, "rBtn4 Selected")
                binding.radioButtonOption4.text = correctRomanization
            }
        }
    }

    //==========================================================================================
    // HandleUserInteraction function
    //==========================================================================================

    /**
     * Void function that handles the user interaction with the views on the layout, it also calls
     * the respective functions that will handle the creation of AlertDialogs, that pops up depending
     * on the users answer (if it's  correct or not, based on the @param correctRomanization and
     * the value of the selectedRomanization variable).
     * @param correctRomanization: String? - Contains the string received from the shuffleList
     * function.
     * @author mathsemilio
     */
    private fun handleUserInteraction(correctRomanization: String) {
        var radioButton: RadioButton
        var selectedRomanization: String

        /*
        Setting a setOnCheckedChangeListener for the radio group to check if any of the buttons is
        selected, if no button is selected, the buttonVerificar Button will be disabled, else the
        button will be enabled, and a onClickListener will be attached to it. Inside the listener
        we will check if the selected romanization (the text from the selected radio button) is
        equal to the correct romanization (passed from the shuffleList function).
        If it is, the dialog alerting the user that his answer is correct will be displayed, and the
        gameScore will be updated. Or else, the dialog alerting the opposite is displayed. In both
        cases the radio button selection will be cleared.
         */
        binding.radioGroupKatakanaOptions.setOnCheckedChangeListener { group, checkedId ->
            if (group.checkedRadioButtonId == -1) {
                binding.buttonVerificar.isEnabled = false
            } else {
                binding.buttonVerificar.isEnabled = true
                radioButton = group.findViewById(checkedId)
                selectedRomanization = radioButton.text.toString()

                binding.buttonVerificar.setOnClickListener {
                    if (selectedRomanization == correctRomanization) {
                        alertDialogCorrectUserInput(correctRomanization)
                        ++gameScore
                        binding.textScoreNumber.text = gameScore.toString()
                        group.clearCheck()
                    } else {
                        alertDialogWrongUserInput(correctRomanization, selectedRomanization)
                        group.clearCheck()
                    }
                }
            }
        }
    }

    //==========================================================================================
    // Toolbar configuration
    //==========================================================================================

    /**
     * Void function that sets up the toolbar for the activity and also sets the title.
     * @author mathsemilio
     */
    private fun setUpActivityToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar_main_activity))
        supportActionBar?.setTitle(R.string.app_name)
    }

    /**
     * Overridden function for inflating a menu on the activity's toolbar
     * @param menu: Menu?
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.toolbar_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Overridden function that will handle the user interaction with the actions on the Toolbar
     * @param item: MenuItem
     */
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_sair -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    //==========================================================================================
    // AlertDialogCorrectUserInput function
    //==========================================================================================

    /**
     * Void function that builds a AlertDialog, which pops up when the user has selected the
     * correct romanization for the symbol on the screen.
     * @param correctRomanization: String - Contains the correct romanization for the symbol on the
     * screen.
     * @author mathsemilio
     */
    private fun alertDialogCorrectUserInput(correctRomanization: String) {
        Log.i(TAG_MAIN_ACTIVITY, "Building the alertDialogCorrectUserInput")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.resposta_correta)
        builder.setMessage(getString(R.string.resposta_correta_msg, correctRomanization))
        builder.setPositiveButton(
            R.string.proximo_simbolo_btnText, DialogInterface
                .OnClickListener(funShuffleList)
        )
        builder.setCancelable(false)
        builder.show()
    }

    //==========================================================================================
    // AlertDialogWrongUserInput function
    //==========================================================================================

    /**
     * Void function that builds a AlertDialog which pops up when the user chooses the wrong
     * romanization for the symbol on the screen.
     * @param correctRomanization: String - Contains the String with the correct romanization for
     * the symbol on the screen.
     * @param selectedRomanization: String - Contains the String with the selected romanization
     * that the user chose from the options on the screen (in this case is the WRONG romanization).
     * @author mathsemilio
     */
    private fun alertDialogWrongUserInput(
        correctRomanization: String,
        selectedRomanization: String
    ) {
        Log.i(TAG_MAIN_ACTIVITY, "Building the alertDialogWrongUserInput")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.resposta_incorreta)
        builder.setMessage(
            getString(
                R.string.resposta_errada_msg, correctRomanization,
                selectedRomanization
            )
        )
        builder.setPositiveButton(
            R.string.proximo_simbolo_btnText, DialogInterface
                .OnClickListener(funShuffleList)
        )
        builder.setCancelable(false)
        builder.show()
    }

    //==========================================================================================
    // onBackPressed
    //==========================================================================================

    /**
     * Overridden void function that will finish the activity once the user presses the back button
     * on his device.
     */
    override fun onBackPressed() {
        Log.i(TAG_MAIN_ACTIVITY, "onBackPressed Called")
        finish()
        super.onBackPressed()
    }

    //==========================================================================================
    // onSaveInstanceState function
    //==========================================================================================

    /**
     * Overridden void function that saves the current activity state (the current symbol, it's
     * romanization and the game score) on a Bundle object.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        // Saving the current katakana symbol, it's associated hepburn romanization and the game score
        outState.putInt(SAVED_SYMBOL_ID, katakanaSymbol.first().imageSymbolId)
        outState.putString(SAVED_ROMANIZATION, katakanaSymbol.first().romanization)
        outState.putInt(SAVED_GAME_SCORE, gameScore)

        super.onSaveInstanceState(outState)
    }

    //==========================================================================================
    // onPause
    //==========================================================================================

    /**
     * Overridden function that is called when the activity is paused. In this case, we are checking
     * if the current game score is higher than the value stored on the SharedPreferences object.
     * If it is, the saveGameScore function is called to update the highest score.
     */
    override fun onPause() {
        Log.i(TAG_MAIN_ACTIVITY, "OnPause called")

        val valueStoredSharedPreferences = SharedPreferencesGameScore(this)
            .retrieveGameScore(GAME_SCORE_KEY)

        if (gameScore > valueStoredSharedPreferences!!) {
            SharedPreferencesGameScore(this).saveGameScore(GAME_SCORE_KEY, gameScore)
        }

        super.onPause()
    }

    //==========================================================================================
    // onDestroy
    //==========================================================================================

    /**
     * Overridden function that is called when the activity is about to be destroyed. In this case
     * we are clearing the katakana symbols list.
     */
    override fun onDestroy() {
        Log.i(TAG_MAIN_ACTIVITY, "OnDestroyed called")
        katakanaSymbol.clear()
        super.onDestroy()
    }
}