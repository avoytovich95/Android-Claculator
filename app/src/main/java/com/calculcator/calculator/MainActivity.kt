package com.calculcator.calculator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

  private lateinit var vibe: Vibrator

  lateinit var mainTextView: TextView
  lateinit var smallTextView: TextView

  var count = 0
  var evaluated = false
  val last = ArrayList<String>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.content_main)

    vibe = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    (findViewById<Button>(R.id.divide)).setOnClickListener { append(" ÷ ", Type.OPERATOR) }
    (findViewById<Button>(R.id.multiply)).setOnClickListener { append(" × ", Type.OPERATOR) }
    (findViewById<Button>(R.id.subtract)).setOnClickListener { append(" − ", Type.OPERATOR) }
    (findViewById<Button>(R.id.add)).setOnClickListener { append(" + ", Type.OPERATOR) }
    (findViewById<Button>(R.id.period)).setOnClickListener { append(".", Type.PERIOD) }
    (findViewById<Button>(R.id.negative)).setOnClickListener { append("-", Type.NEGATIVE) }
    (findViewById<Button>(R.id.clear)).setOnClickListener { clear(); v() }
    (findViewById<Button>(R.id.delete)).setOnClickListener { delete(); v() }
    (findViewById<Button>(R.id.equals)).setOnClickListener { equals(); v() }
    (findViewById<Button>(R.id.percent)).setOnClickListener { perc(); v() }

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

  private fun v() {
    vibe.vibrate(5)
  }

  fun append(str: String, type: Type) {
    v()
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

  fun softClear() {
    if (evaluated) {
      smallTextView.text = ""
      evaluated = false
      last.clear()
    }
  }

  private fun appendNum(num: String) {
    softClear()

    if (mainTextView.text.isEmpty())
      count ++
    else if (mainTextView.text.last() == ' ')
      count++
    mainTextView.append(num)
  }

  private fun appendOp(op: String) {
    if (mainTextView.text.last() != ' ') {
      softClear()

      count++

      if (mainTextView.text.last() == '.')
        mainTextView.append("0")

      mainTextView.append(op)
    }
  }

  private fun appendDot() {
    if (count == 0) {
      softClear()

      mainTextView.append("0.")
      count++

    } else if (mainTextView.text.last() == ' ') {
      mainTextView.append("0.")
    }else if (count == 1 && !mainTextView.text.contains(".")) {
      mainTextView.append(".")
    }else if (!mainTextView.text.split(" ").last().contains(".")) {
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
    if (count > 2) {
      smallTextView.text = mainTextView.text
      mainTextView.text = eval()
    } else if (evaluated) {
      smallTextView.text = mainTextView.text
      smallTextView.append(last[0])
      smallTextView.append(last[1])
      evalLast()
    }
  }

  fun perc() {
    if (count > 2) {
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
    if (mainTextView.text.last() == '.') {
      mainTextView.append("0")
      smallTextView.append("0")
    }
    smallTextView.append(" = ")

    val str = mainTextView.text.split(" ")

    last += str[str.size - 2]
    last += str[str.size - 1]

    val eq = ArrayList<String>()
    for (s in str)
      eq += s
    val res = Functions.solve(eq)

    count = 1
    evaluated = true

    return res
  }

  fun evalLast() {

  }
}
