package com.calculcator.calculator

object Functions {

  fun solve(eq: ArrayList<String>): String {
    val temp = ArrayList<String>(eq)
    val temp2 = ArrayList<String>()

    while (temp.contains("×") || temp.contains("÷")){\

      if (temp.contains("×")) {
        val m = temp.indexOf("×")
        temp[m - 1] = solve(temp[m - 1], temp.removeAt(m + 1), temp.removeAt(m))
      }
      if (temp.contains("÷")) {
        val d = temp.indexOf("÷")
        temp[d - 1] = solve(temp[d - 1], temp.removeAt(d + 1), temp.removeAt(d))
      }
    }

    while(temp.size != 1) {
      println(temp)
      if (temp.contains("+")) {
        val p = temp.indexOf("+")
        temp[p - 1] = solve(temp[p - 1], temp.removeAt(p + 1), temp.removeAt(p))
      }
      if (temp.contains("−")) {
        val s = temp.indexOf("−")
        temp[s - 1] = solve(temp[s - 1], temp.removeAt(s + 1), temp.removeAt(s))
      }
    }

    return temp[0]
  }

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