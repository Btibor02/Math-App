package com.example.mathapp.domain

data class Question(
    val a: Int,
    val b: Int,
    val op: Operation,
    val correct: Int,
    val choices: List<Int>
)
