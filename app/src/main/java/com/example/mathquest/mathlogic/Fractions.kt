package com.example.mathquest.mathlogic

import kotlin.math.round
import kotlin.random.Random


//** HOW TO USE **

//export AdditionLogic obj
//call com.example.mathquest.mathlogic.Fractions
//call AdditionFractionLogic.generateQuestion(grade =?) -> grade ?-? gives diff difficulty level
//then verify user answer with AnswerVerifier.verifyAnswer(studentAnswer)
//or get the correct answer with VerifyFractionAnswer.getRightAnswer()

object AdditionFractionLogic{

    private fun additionRange(grade: Int): Pair<Int, Int>{

        //depending on the grade random gen will make questions with these ranges
        return when (grade){
            3 -> Random.nextInt(1, 5) to Random.nextInt(2, 10)
            4 -> Random.nextInt(5, 30) to Random.nextInt(10, 200)
            else -> Random.nextInt(1, 5) to Random.nextInt(2, 10)
        }
    }

    private fun simplify(numerator: Int, denominator: Int):  Pair<Int, Int>{
        fun gcd(a: Int, b: Int): Int{
            return if (b == 0) a else gcd(b, a % b)
        }
        val dividor = gcd(numerator, denominator)
        return numerator /dividor to denominator /dividor
    }


    fun generateFractionAddition(grade: Int): String{
        val (n1, d1) = additionRange(grade)
        val (n2, d2) = additionRange(grade)
        val (numerator, denominator) = if (grade == 3){
           val sameDenominator = d1
           val num = n1 + n2
           simplify(num, sameDenominator)
        }else{
            val sameDenominator = d1 * d2
            val num = n1 * d2 + n2 * d1
            simplify(num, sameDenominator)
        }
        AnswerVerifier.setCorrectAnswer(numerator.toDouble() /denominator.toDouble())
        return "$n1/$d1 + $n2/$d2"
    }

}

object SubstractionFractionLogic{

}

//answers can be in both decimal and fraction ex.(0,5 ; 1/2)
object VerifyFractionAnswer{

    fun verifyAnswer(studentAnswer: String): Boolean{
        val numberVal = try{
            if (studentAnswer.contains("/")){
                val answerNumbers = studentAnswer.split("/")
                val numerator = answerNumbers[0].trim().toDouble()
                val denominator = answerNumbers[1].trim().toDouble()

                numerator/denominator
            }else{
                studentAnswer.toDouble()
            }
        }catch(e: Exception){
            return false
        }
        val correct = AnswerVerifier.getRightAnswer()
        return if (correct != null){
            round(correct*100)/ 100 == round(numberVal* 100)/ 100
        }else false
    }
}


