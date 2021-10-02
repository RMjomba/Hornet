package com.reginaldateya.mymemory.models

import android.media.Image
import com.reginaldateya.mymemory.utils.DEFAULT_ICONS

private lateinit var cards: Image

class MemoryGame(boardSize : BoardSize) {


    val card: List<MemoryCard>
    var numPairsFound = 0


    private var numCardFlips = 0
    private var indexOfSingleSelectedCard: Int? = null

    init {
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        card = randomizedImages.map { MemoryCard(it) }
    }
    fun flipCard(position : Int) : Boolean {
        numCardFlips++
        val card = card[position]
        // Three cases:
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 cards previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over => restore cards + flip over the selected card
        var foundMatch = false
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 cards previously flipped over
            restoreCards()
            indexOfSingleSelectedCard = position

        } else {
            // exactly one card previously flipped over
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }

        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkForMatch(position1 : Int, position2 : Int) : Boolean {
        if (card[position1].identifier != card[position2].identifier){
            return false
        }
        card[position1].isMatched = true
        card[position2].isMatched = true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for (card : MemoryCard in card) {
            if (!card.isMatched){
                card.isFaceUp = false

            }

        }
    }

        fun haveWonGame(boardSize : BoardSize) : Boolean {
        return numPairsFound == boardSize.getNumPairs()
        }

    fun isCardFaceUp(position: Int) : Boolean {
        return card[position].isFaceUp

    }

    fun getNumMoves() : Int {
        return numCardFlips / 2
    }

}