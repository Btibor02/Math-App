package com.example.mathquest.mathlogic

import kotlin.random.Random
import kotlin.math.round


//** HOW TO USE **

//export AdditionLogic obj
//call com.example.mathquest.mathlogic.Arithmetics
//call AdditionLogic.generateQuestion(grade =?) -> grade 1-4 gives diff difficulty level
//then verify user answer with AnswerVerifier.verifyAnswer(studentAnswer)
//or get the correct answer with AnswerVerifier.getRightAnswer()

object AdditionLogic{
    private fun additionRange(grade: Int): IntRange{

        //depending on the grade random gen will make questions with these ranges
        return when (grade){
             1 -> 0..9
            2 -> 10..99
            3 -> 100..999
            4 -> 1000..9999
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
//call substractionLogic.generateQuestion(grade =?) -> grade 1-4 gives diff difficulty level

object SubstractionLogic{
    private fun substractionRange(grade: Int): IntRange{

        //depending on the grade random gen will make questions with these ranges
        return when (grade){
            1 -> 0..9
            2 -> 10..99
            3 -> 100..999
            4 -> 1000..9999
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
        return "$a - $b"
    }
}

//export MultiplicationLogic obj
//call com.example.mathquest.mathlogic.Arithmetics
//call MultiplicationLogic.generateQuestion(grade =?) -> grade 2-4 gives diff difficulty level

object MultiplicationLogic{

    private fun multiplicationRange(grade : Int): IntRange{
        //placeholders for now
        return when (grade){
            2 -> 0..12
            3 -> 0..50
            4 -> 11..200
            else -> 0..12
        }
    }

    fun generateQuestion(grade: Int): String{
        val range = multiplicationRange(grade)

        val a = Random.nextInt(range.first, range.last +1)
        val b = Random.nextInt(range.first, range.last +1)

        AnswerVerifier.setCorrectAnswer(a * b)
        return "$a * $b"
    }
}

//export DivisionLogic obj
//call com.example.mathquest.mathlogic.Arithmetics
//call DivisionLogic.generateQuestion(grade =?) -> grade 2-4 gives diff difficulty level

object DivisionLogic{

    private fun divisionRange(grade : Int): IntRange{
        //placeholders for now
        return when (grade){
            2 -> 1..10
            3 -> 1..50
            4 -> 11..200
            else -> 0..12
        }
    }

    fun generateQuestion(grade: Int): String{
        val range = divisionRange(grade)

        var a = Random.nextInt(range.first, range.last +1)
        var b = Random.nextInt(range.first, range.last +1)

        //for easier grade dont divide smaller with bigger number
        if (grade == 2 && a <b){
            val temp = a
            a = b
            b = temp
        }

        val answer = round((a.toDouble()/ b) * 100)/100
        AnswerVerifier.setCorrectAnswer(answer)

        return "$a / $b"
    }
}

object AnswerVerifier{
    private var correctAnswer: Double? = null

    //store corect answer
    fun setCorrectAnswer(answer: Number){
        correctAnswer = answer.toDouble()
    }

    //if studetn answer is wring return false Bollean
    fun verifyAnswer(studentAnswer: Number): Boolean{
        return correctAnswer == studentAnswer.toDouble()
    }

    fun getRightAnswer(): Double? = correctAnswer
}
