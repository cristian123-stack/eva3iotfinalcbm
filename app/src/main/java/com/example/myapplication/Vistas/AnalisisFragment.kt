package com.example.myapplication.Vistas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth

class AnalisisFragment : Fragment() {

    //firebase
    private lateinit var auth: FirebaseAuth

    private var consumoActual = 0.0f
    private var consumoTextView: TextView? = null
    private var costoTextView: TextView? = null
    private var horasUsoTextView: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        return inflater.inflate(R.layout.fragment_analisis, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //firebase
        auth = FirebaseAuth.getInstance()

        // Asignar referencias a las vistas
        consumoTextView = view.findViewById(R.id.consumo_actual)
        costoTextView = view.findViewById(R.id.costo_aproximado)
        horasUsoTextView = view.findViewById(R.id.horas_uso)
        progressBar = view.findViewById(R.id.progressBar)

        // Configurar botones
        val btnConsumo = view.findViewById<Button>(R.id.btnConsumo)
        btnConsumo.setOnClickListener {
            mostrarConsumo()
        }

        //val btnHome = view.findViewById<Button>(R.id.btnHome)
        //btnHome.setOnClickListener {
        //    activity?.finish() // Finaliza la actividad asociada si es necesario
        //}
    }

    private fun mostrarConsumo() {
        consumoActual = (Math.random() * 10 + 1).toFloat()
        consumoTextView?.text = "Consumo Actual: " + String.format("%.2f", consumoActual) + " kWh"

        val costoAproximado = calcularCosto(consumoActual)
        costoTextView?.text = "Costo Aproximado: $" + String.format("%.2f", costoAproximado)

        val progreso = (consumoActual / 10 * 100).toInt()
        progressBar?.progress = progreso

        mostrarMensajeConsumo(progreso)
        calcularHorasUso()
    }

    private fun calcularCosto(consumo: Float): Float {
        val tarifaPorKWh = 150.0f
        return consumo * tarifaPorKWh
    }

    private fun calcularHorasUso() {
        horasUsoTextView?.text = "Horas de uso: " + String.format("%.2f", consumoActual)
    }

    private fun mostrarMensajeConsumo(progreso: Int) {
        when {
            progreso > 80 -> {
                Toast.makeText(context, "Consumo Alto", Toast.LENGTH_SHORT).show()
            }
            progreso > 50 -> {
                Toast.makeText(context, "Consumo Moderado", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Consumo Bajo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Método de fábrica para crear una nueva instancia del fragmento.
         */
        @JvmStatic
        fun newInstance() = AnalisisFragment()
    }
}