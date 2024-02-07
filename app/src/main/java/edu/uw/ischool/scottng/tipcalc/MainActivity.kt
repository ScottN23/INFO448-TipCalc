package edu.uw.ischool.scottng.tipcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var serviceCharge: EditText
    private lateinit var tipButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceCharge = findViewById(R.id.serviceCharge)
        tipButton = findViewById(R.id.tipButton)

        tipButton.isEnabled = false

        serviceCharge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() && !s.startsWith("$")) {
                    val formattedAmount = formatCurrency(s.toString())
                    serviceCharge.setText(formattedAmount)
                    serviceCharge.setSelection(serviceCharge.text.length)
                }

                tipButton.isEnabled = s?.isNotEmpty() == true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        tipButton.setOnClickListener {
            val serviceChargeValue = serviceCharge.text.toString().replace("$", "").toDouble()
            val tipAmount = serviceChargeValue * 0.15

            val formattedTip = String.format("$%.2f", tipAmount)
            val toast = Toast.makeText(this, formattedTip, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    private fun formatCurrency(value: String): String {
        val decimalFormat = DecimalFormat("$#,##0.00")
        return decimalFormat.format(getNumericValue(value))
    }

    private fun getNumericValue(value: String): Double {
        val regex = Regex("^\\$?\\d*\\.?\\d{0,2}\$")
        val matchResult = regex.find(value)

        return matchResult?.value?.toDoubleOrNull() ?: 0.0
    }
}
