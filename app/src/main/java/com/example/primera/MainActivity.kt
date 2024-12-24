package com.example.primera

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.primera.modelo.CuentaMesa
import com.example.primera.modelo.ItemMenu
import com.example.primera.modelo.ItemMesa
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var etCantidadPastel: EditText?     = null
    private var etCantidadCazuelas: EditText?   = null
    private var tvValorComida: TextView? = null
    private var tvValorPropina: TextView? = null
    private var tvValorTotal: TextView? = null
    private var switchPropina: SwitchCompat? = null
    private var propina: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etCantidadPastel   = findViewById(R.id.etCantidadPastel)
        etCantidadCazuelas = findViewById(R.id.etCantidadCazuelas)
        tvValorComida      = findViewById(R.id.tvValorComida)
        tvValorPropina     = findViewById(R.id.tvValorPropina)
        tvValorTotal       = findViewById(R.id.tvValorTotal)
        switchPropina      = findViewById(R.id.sPropina)


        val textWatcher: TextWatcher = object:TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (propina)
                    valores()
                else
                    valores()
            }
        }

        etCantidadPastel?.addTextChangedListener(textWatcher)
        etCantidadCazuelas?.addTextChangedListener(textWatcher)
        switchPropina?.setOnCheckedChangeListener { _, isChecked ->
            propina = isChecked
            valores()
        }

    }

    private fun calculoCuenta(): CuentaMesa {
        val comida1 = ItemMenu("Pastel de Choclo", "36000")
        val comida2 = ItemMenu("Cazuela", "10000")

        val cantidadPastel = etCantidadPastel?.text.toString().toIntOrNull() ?: 0
        val cantidadCazuela = etCantidadCazuelas?.text.toString().toIntOrNull() ?: 0

        val comidaPastel  = ItemMesa(cantidadPastel, comida1)
        val comidaCazuela = ItemMesa(cantidadCazuela, comida2)

        val cuentaTotal = CuentaMesa(mesa = 1)

        cuentaTotal.agregarItem(comidaPastel)
        cuentaTotal.agregarItem(comidaCazuela)

        return cuentaTotal

    }

    private fun calculoSinPropina() = calculoCuenta().calcularTotalSinPropina()
    private fun calculoPropina() = calculoCuenta().calcularPropina()
    private fun calculoTotal() = calculoCuenta().calcularTotalConPropina()
    private fun formatoCLP(): NumberFormat {
        val formatCLP = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        return formatCLP
    }
    fun valores () {

        val valorComida = formatoCLP().format(calculoSinPropina())
        val valorPropina = if (propina) formatoCLP().format(calculoPropina())
                            else formatoCLP().format(0)
        val valorTotal = if (propina) formatoCLP().format(calculoTotal())
                            else formatoCLP().format(calculoSinPropina())

        tvValorComida?.text = valorComida
        tvValorPropina?.text = valorPropina
        tvValorTotal?.text = valorTotal


    }


}