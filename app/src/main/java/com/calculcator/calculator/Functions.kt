package com.calculcator.calculator

object Functions {

  fun solve(num1: String, num2: String, op: String): String {
    return when (op) {
      "+" -> add(num1.toDouble(), num2.toDouble())
      "−" -> sub(num1.toDouble(), num2.toDouble())
      "×" -> mul(num1.toDouble(), num2.toDouble())
      "÷" -> div(num1.toDouble(), num2.toDouble())
      else -> "Err"
    }
  }

  private fun add(num1: Double, num2: Double): String {
    val res = num1 + num2
    return if (res == Math.floor(res))
      res.toInt().toString()
    else
      res.toString()
  }

  private fun sub(num1: Double, num2: Double): String {
    val res = num1 - num2
    return if (res == Math.floor(res))
      res.toInt().toString()
    else
      res.toString()
  }

  private fun mul(num1: Double, num2: Double): String {
    val res = num1 * num2
    return if (res == Math.floor(res))
      res.toInt().toString()
    else
      res.toString()
  }

  private fun div(num1: Double, num2: Double): String {
    if (num2 == 0.0)
      return "Cannot divide by 0"

    val res = num1 / num2
    return if (res == Math.floor(res))
      res.toInt().toString()
    else res.toString()
  }

}