package com.example.mathquest.mathlogic

import kotlin.random.Random
import kotlin.random.nextInt


//HOW TO USE

//export AdditionLogic obj
//call com.example.mathquest.mathlogic.Arithmetics
//call AdditionLogic.generateQuestion(grade =?) -> grade 1-3 gives diff difficulty level
//then verify user answer with AnswerVerifier.verifyAnswer(studentAnswer)
//or get the correct answer with AnswerVerifier.getRightAnswer()

object AdditionLogic{
    private fun additionRange(grade: Int): IntRange{

        //depending on the grade random gen will make questions with these ranges
        return when (grade){
             1 -> 0..9
            2 -> 10..99
            3 -> 100..999
            else -> 0..9
        }
    }

    fun generateQuestion(grade: Int): String{
        val range = additionRange(grade)

        val a = Random.nextInt(range.first, range.last +1)
        val b = Random.nextInt(range.first, range.last +1)

        AnswerVerifier.setCorrectAnswer(a + b)
        return "$a + $b"
    }

}

//export SubstractionLogic obj
//call com.example.mathquest.mathlogic.Arithmetics
//call substractionLogic.generateQuestion(grade =?) -> grade 1-3 gives diff difficulty level

object SubstractionLogic{
    private fun substractionRange(grade: Int): IntRange{

        //depending on the grade random gen will make questions with these ranges
        return when (grade){
            1 -> 0..9
            2 -> 10..99
            3 -> 100..999
            else -> 0..9
        }
    }

    fun generateQuestion(grade: Int): String{
        val range = substractionRange(grade)

        var a = Random.nextInt(range.first, range.last +1)
        var b = Random.nextInt(range.first, range.last +1)

        //make sure we dont go into negatives
        if (a < b){
            val temp = a
            a = b
            b = temp
        }
        AnswerVerifier.setCorrectAnswer(a-b)
        return "$a + $b"
    }
}


object AnswerVerifier{
    private var correctAnswer: Int? = null

    //store corect answer
    fun setCorrectAnswer(answer: Int){
        correctAnswer = answer
    }

    //if studetn answer is wring return false Bollean
    fun verifyAnswer(studentAnswer: Int): Boolean{
        return correctAnswer == studentAnswer
    }

    fun getRightAnswer(): Int? = correctAnswer
}
