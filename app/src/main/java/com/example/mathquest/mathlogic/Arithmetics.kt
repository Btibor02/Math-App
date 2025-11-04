package com.example.mathquest.mathlogic

import kotlin.random.Random
import kotlin.math.round


//** HOW TO USE **

//export AdditionLogic obj
//call com.example.mathquest.mathlogic.Arithmetics
//call AdditionLogic.generateQuestion(grade =?) -> grade 1-4 gives diff difficulty level
//then verify user answer with AnswerVerifier.verifyAnswer(studentAnswer)
//or get the correct answer with AnswerVerifier.getRightAnswer()

object GradeRanges {
    val additionSubtractionRanges = mapOf(
        1 to 0..20,
        2 to 10..99,
        3 to 50..999,
        4 to 100..9999,
        5 to 1000..99999,
        6 to 10000..99999
    )

    val multiplicationRanges = mapOf(
        2 to 0..12,
        3 to 5..50,
        4 to 10..200,
        5 to 50..500,
        6 to 100..1000
    )

    val divisionRanges = mapOf(
        2 to 1..10,
        3 to 2..50,
        4 to 10..200,
        5 to 50..500,
        6 to 100..1000
    )
}

object AdditionLogic {
    fun generateQuestion(grade: Int): String {
        val range = GradeRanges.additionSubtractionRanges[grade] ?: 0..20
        val a = Random.nextInt(range.first, range.last + 1)
        val b = Random.nextInt(range.first, range.last + 1)
        AnswerVerifier.setCorrectAnswer(a + b)
        return "$a + $b"
    }
}

object SubstractionLogic {
    fun generateQuestion(grade: Int): String {
        val range = GradeRanges.additionSubtractionRanges[grade] ?: 0..20
        var a = Random.nextInt(range.first, range.last + 1)
        var b = Random.nextInt(range.first, range.last + 1)

        if (a < b) {
            val temp = a
            a = b
            b = temp
        }

        AnswerVerifier.setCorrectAnswer(a - b)
        return "$a - $b"
    }
}

object MultiplicationLogic {
    fun generateQuestion(grade: Int): String {
        val range = GradeRanges.multiplicationRanges[grade] ?: 0..12
        val a = Random.nextInt(range.first, range.last + 1)
        val b = Random.nextInt(range.first, range.last + 1)
        AnswerVerifier.setCorrectAnswer(a * b)
        return "$a * $b"
    }
}

object DivisionLogic {
    fun generateQuestion(grade: Int): String {
        val range = GradeRanges.divisionRanges[grade] ?: 1..12
        var a = Random.nextInt(range.first, range.last + 1)
        var b = Random.nextInt(range.first, range.last + 1)

        if (grade <= 3 && a < b) {
            val temp = a
            a = b
            b = temp
        }

        val answer: Number = if (a % b == 0) a / b else round((a.toDouble() / b) * 100) / 100
        AnswerVerifier.setCorrectAnswer(answer)
        return "$a / $b"
    }
}

object AnswerVerifier{
    private var correctAnswer: Number? = null

    //store corect answer
    fun setCorrectAnswer(answer: Number){
        correctAnswer = answer
    }

    //if studetn answer is wring return false Bollean
    fun verifyAnswer(studentAnswer: Number): Boolean{
        val correct = correctAnswer ?: return false
        return if (correct is Double) {
            correct == studentAnswer.toDouble()
        } else {
            correct.toInt() == studentAnswer.toInt()
        }

    }

    fun getRightAnswer(): Number? = correctAnswer
}
