package be.buithg.homework4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var resultView: TextView
    private lateinit var openResultButton: Button

    private var operand: Double? = null
    private var pendingOp: String? = null
    private var input: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        resultView = findViewById(R.id.tv_result)
        openResultButton = findViewById(R.id.btn_open_result)

        val numberIds = intArrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_dot
        )
        for (id in numberIds) {
            findViewById<Button>(id).setOnClickListener(this)
        }

        val opIds = intArrayOf(R.id.btn_add, R.id.btn_sub, R.id.btn_mul, R.id.btn_div)
        for (id in opIds) {
            findViewById<Button>(id).setOnClickListener(opListener)
        }

        findViewById<Button>(R.id.btn_equals).setOnClickListener { onEquals() }
        findViewById<Button>(R.id.btn_clear).setOnClickListener { onClear() }

        openResultButton.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("result", resultView.text.toString())
            startActivity(intent)
        }
    }

    override fun onClick(v: View) {
        val b = v as Button
        input += b.text
        resultView.text = input
        openResultButton.visibility = View.GONE
    }

    private val opListener = View.OnClickListener { v ->
        val b = v as Button
        if (input.isNotEmpty()) {
            val value = input.toDouble()
            if (operand == null) {
                operand = value
            } else if (pendingOp != null) {
                operand = performOperation(operand!!, value, pendingOp!!)
            }
            input = ""
            resultView.text = operand.toString()
        }
        pendingOp = b.text.toString()
        openResultButton.visibility = View.GONE
    }

    private fun onEquals() {
        if (pendingOp != null && input.isNotEmpty()) {
            val value = input.toDouble()
            operand = performOperation(operand ?: 0.0, value, pendingOp!!)
            resultView.text = operand.toString()
            input = operand.toString()
            pendingOp = null
            operand = null
            openResultButton.visibility = View.VISIBLE
        }
    }

    private fun onClear() {
        operand = null
        pendingOp = null
        input = ""
        resultView.text = "0"
        openResultButton.visibility = View.GONE
    }

    private fun performOperation(op1: Double, op2: Double, operator: String): Double {
        return when (operator) {
            "+" -> op1 + op2
            "-" -> op1 - op2
            "*" -> op1 * op2
            "/" -> op1 / op2
            else -> op2
        }
    }
}
