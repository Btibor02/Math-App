package com.example.mathapp.domain

import kotlin.random.Random

object QuestionGenerator {
    fun generateSet(grade: GradeLevel, op: Operation, count: Int = 10): List<Question> =
        List(count) { generate(grade, op) }

    fun generate(grade: GradeLevel, op: Operation): Question {
        val (min, max) = when (grade) {
            GradeLevel.G1 -> 0 to 10
            GradeLevel.G2 -> 0 to 20
            GradeLevel.G3 -> 0 to 50
            GradeLevel.G4 -> 0 to 100
            GradeLevel.G5 -> 0 to 200
            GradeLevel.G6 -> 0 to 500
        }
        var a = Random.nextInt(min, max + 1)
        var b = Random.nextInt(min, max + 1)

        val correct = when (op) {
            Operation.ADD -> a + b
            Operation.SUB -> { if (a < b) a = b.also { b = a }; a - b }
            Operation.MUL -> (a % 12) * (b % 12)
            Operation.DIV -> {
                val divisor = (b % 11).coerceAtLeast(1)
                val q = (a % 12)
                a = divisor * q; b = divisor; q
            }
        }

        val choices = buildList {
            add(correct)
            while (size < 4) {
                val delta = (Random.nextInt(-5, 6)).let { if (it == 0) 1 else it }
                val opt = correct + delta
                if (opt !in this) add(opt)
            }
            shuffle()
        }
        return Question(a, b, op, correct, choices)
    }
}
