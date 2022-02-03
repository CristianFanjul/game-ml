package com.cmf.gameml.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cmf.gameml.R
import com.cmf.gameml.databinding.ActivityMainBinding
import com.cmf.gameml.model.Player
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var gameFinished: Boolean = false
    val player1 = Player("Cristian", Type.CROSS)
    val player2 = Player("Oponent", Type.CIRCLE)

    var turn: Turn = Turn.PLAYER_1

    private val positions: MutableList<Type> = mutableListOf(
        Type.EMPTY,
        Type.EMPTY,
        Type.EMPTY,
        Type.EMPTY,
        Type.EMPTY,
        Type.EMPTY,
        Type.EMPTY,
        Type.EMPTY,
        Type.EMPTY
    )

    // Win positions 0,1,2; 3,4,5; 6,7,8; 0,3,6; 2,4,7; 2,5,8; 0,4,8; 2,4,6


    // Store all the Winning conditions in 2D array
    private val winningPos = listOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(2, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initListeners()
    }

    private fun initListeners() {
        binding.img11.setOnClickListener {
            checkTurnAndDraw(binding.img11, 0)
        }
        binding.img12.setOnClickListener {
            checkTurnAndDraw(binding.img12, 1)
        }
        binding.img13.setOnClickListener {
            checkTurnAndDraw(binding.img13, 2)
        }
        binding.img21.setOnClickListener {
            checkTurnAndDraw(binding.img21, 3)
        }
        binding.img22.setOnClickListener {
            checkTurnAndDraw(binding.img22, 4)
        }
        binding.img23.setOnClickListener {
            checkTurnAndDraw(binding.img23, 5)
        }
        binding.img31.setOnClickListener {
            checkTurnAndDraw(binding.img31, 6)
        }
        binding.img32.setOnClickListener {
            checkTurnAndDraw(binding.img32, 7)
        }
        binding.img33.setOnClickListener {
            checkTurnAndDraw(binding.img33, 8)
        }
    }

    private fun checkTurnAndDraw(img: ImageView, position: Int) {
        if (!gameFinished) {
            if (positions[position] == Type.EMPTY) {
                turn = if (turn == Turn.PLAYER_1) {
                    img.setImageResource(R.drawable.cross)
                    positions[position] = Type.CROSS
                    Turn.PLAYER_2
                } else {
                    img.setImageResource(R.drawable.circle)
                    positions[position] = Type.CIRCLE
                    Turn.PLAYER_1
                }
                checkIfWins()
            }
        }
    }

    private fun checkIfWins() {
        winningPos.forEach { array ->
            if (isCrossOrCircle(array) && isSameShape(array)) {
                val winner = if (positions[array[0]] == Type.CROSS) {
                    player1
                } else player2
                Toast.makeText(this, "Winner ${winner.name}", Toast.LENGTH_LONG).show()
                gameFinished = true
            }

        }
    }

    private fun isCrossOrCircle(array: IntArray): Boolean {
        val pos0 = array[0]
        val pos1 = array[1]
        val pos2 = array[2]

        return positions[pos0] != Type.EMPTY && positions[pos1] != Type.EMPTY && positions[pos2] != Type.EMPTY
    }

    private fun isSameShape(array: IntArray): Boolean {
        val pos0 = array[0]
        val pos1 = array[1]
        val pos2 = array[2]

        return positions[pos0] == positions[pos1] && positions[pos1] == positions[pos2]
    }

}

enum class Type {
    EMPTY, CROSS, CIRCLE
}

enum class Turn {
    PLAYER_1, PLAYER_2
}