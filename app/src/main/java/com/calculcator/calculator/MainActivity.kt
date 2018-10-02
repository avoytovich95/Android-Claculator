package com.calculcator.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

  lateinit var mainTextView: TextView
  lateinit var smallTextView: TextView

  var count = 0
  var evaluated = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.content_main)

    (findViewById<Button>(R.id.divide)).setOnClickListener { append(" ÷ ", Type.OPERATOR) }
    (findViewById<Button>(R.id.multiply)).setOnClickListener { append(" × ", Type.OPERATOR) }
    (findViewById<Button>(R.id.subtract)).setOnClickListener { append(" − ", Type.OPERATOR) }
    (findViewById<Button>(R.id.add)).setOnClickListener { append(" + ", Type.OPERATOR) }
    (findViewById<Button>(R.id.period)).setOnClickListener { append(".", Type.PERIOD) }
    (findViewById<Button>(R.id.negative)).setOnClickListener { append("-", Type.NEGATIVE) }
    (findViewById<Button>(R.id.clear)).setOnClickListener { clear() }
    (findViewById<Button>(R.id.delete)).setOnClickListener { delete() }
    (findViewById<Button>(R.id.equals)).setOnClickListener { equals() }
    (findViewById<Button>(R.id.percent)).setOnClickListener { perc() }

    (findViewById<Button>(R.id.zero)).setOnClickListener { append("0", Type.NUMBER) }
    (findViewById<Button>(R.id.one)).setOnClickListener { append("1", Type.NUMBER) }
    (findViewById<Button>(R.id.two)).setOnClickListener { append("2", Type.NUMBER) }
    (findViewById<Button>(R.id.three)).setOnClickListener { append("3", Type.NUMBER) }
    (findViewById<Button>(R.id.four)).setOnClickListener { append("4", Type.NUMBER) }
    (findViewById<Button>(R.id.five)).setOnClickListener { append("5", Type.NUMBER) }
    (findViewById<Button>(R.id.six)).setOnClickListener { append("6", Type.NUMBER) }
    (findViewById<Button>(R.id.seven)).setOnClickListener { append("7", Type.NUMBER) }
    (findViewById<Button>(R.id.eight)).setOnClickListener { append("8", Type.NUMBER) }
    (findViewById<Button>(R.id.nine)).setOnClickListener { append("9", Type.NUMBER) }

    mainTextView = findViewById(R.id.mainText)
    smallTextView = findViewById(R.id.smallText)
  }

  fun append(str: String, type: Type) {
    when (type) {
      Type.NUMBER -> appendNum(str)
      Type.OPERATOR -> appendOp(str)
      Type.PERIOD -> appendDot()
      Type.NEGATIVE -> makeNeg()
    }
  }

  fun clear() {
    mainTextView.text = ""
    smallTextView.text = ""
    count = 0
  }

  fun appendNum(num: String) {
    if (evaluated) {
      evaluated = false
      clear()
    }

    mainTextView.append(num)
    if (count == 0)
      count++
  }

  fun appendOp(op: String) {
    if (count == 1) {
      count++

      if (mainTextView.text.last() == '.')
        mainTextView.append("0")

      mainTextView.append(op)
    }
  }

  fun appendDot() {
    if (count == 0) {
      if (evaluated) {
        evaluated = false
        clear()
      }

      mainTextView.append("0.")
      count++

    } else if (count == 1 && !mainTextView.text.contains("."))
      mainTextView.append(".")

    else if (count == 2) {

      if (mainTextView.text.split(" ")[2].isEmpty())
        mainTextView.append("0.")

      else if (!mainTextView.text.split(" ")[2].contains("."))
        mainTextView.append(".")
    }
  }

  @SuppressLint("SetTextI18n")
  fun makeNeg() {
    if (count == 1) {
      val num = mainTextView.text
      if (num.first() == '-')
        mainTextView.text = num.substring(1)
      else
        mainTextView.text = "-$num"
    }
    else if (count >= 2) {
      val str = mainTextView.text.split(" ")
      if (str[2].first() == '-')
        mainTextView.text = "${str[0]} ${str[1]} ${str[2].substring(1)}"
      else
        mainTextView.text = "${str[0]} ${str[1]} -${str[2]}"
    }
  }

  fun delete() {
    if (mainTextView.text.isNotEmpty()) {
      if (mainTextView.text.last() == ' ') {
        mainTextView.text = mainTextView.text.substring(0, mainTextView.text.length - 3)
        count--
      }
      else {
        mainTextView.text = mainTextView.text.substring(0, mainTextView.text.length - 1)
        if (mainTextView.text.isEmpty())
          count--
      }
    }
  }

  fun equals() {
    if (count == 2) {
      smallTextView.text = mainTextView.text
      mainTextView.text = eval()
    }
  }

  fun perc() {
    if (count == 2) {
      smallTextView.append("(")
      smallTextView.append(mainTextView.text)
      smallTextView.append(")%")

      mainTextView.text = (eval().toDouble() / 100).toString()
    } else if (count == 1) {
      if (mainTextView.text.last() == '.')
        mainTextView.append("0")
      smallTextView.append(mainTextView.text)
      smallTextView.append("%")

      val num = mainTextView.text.toString()
      mainTextView.text = (num.toDouble() / 100).toString()
    }
  }

  fun eval(): String {
    if (mainTextView.text.last() == '.')
      mainTextView.append("0")

    val str = mainTextView.text.split(" ")
    count = 0

    val res = Functions.solve(str[0], str[2], str[1])
    smallTextView.append(" = ")
    evaluated = true
    return res
  }
}
