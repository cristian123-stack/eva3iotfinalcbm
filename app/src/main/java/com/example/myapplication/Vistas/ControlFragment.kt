package com.example.myapplication.Vistas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth

class ControlFragment : Fragment() {

    //firebase
    private lateinit var auth: FirebaseAuth

    // Definir valor por defecto para los botones de luces y cerraduras
    private var luzXdefecto = 0
    private var cerraduraXdefecto = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragment
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //firebase
        auth = FirebaseAuth.getInstance()

        // Obtener las referencias a los botones y vistas
        val luzOnOff = view.findViewById<Button>(R.id.btnLuces)
        val cerraduraLU = view.findViewById<Button>(R.id.btnCandado)

        // Listener para el botón de luces
        luzOnOff.setOnClickListener {
            fnOnOff(view)
        }

        // Listener para el botón de cerradura
        cerraduraLU.setOnClickListener {
            fnLockUnlock(view)
        }
    }

    private fun fnOnOff(view: View) {
        try {
            val lgtState: ImageView = view.findViewById(R.id.imgLuz)
            val lgtMsg: Button = view.findViewById(R.id.btnLuces)

            when (luzXdefecto) {
                0 -> {
                    lgtState.setImageResource(R.drawable.lighton)
                    lgtMsg.text = "On"
                }
                1 -> {
                    lgtState.setImageResource(R.drawable.lightoff)
                    lgtMsg.text = "Off"
                }
            }

            luzXdefecto = if (luzXdefecto == 0) 1 else 0
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }

    private fun fnLockUnlock(view: View) {
        try {
            val lockState: ImageView = view.findViewById(R.id.imgCandado)
            val lockMsg: Button = view.findViewById(R.id.btnCandado)

            when (cerraduraXdefecto) {
                0 -> {
                    lockState.setImageResource(R.drawable.lock)
                    lockMsg.text = "Locked"
                }
                1 -> {
                    lockState.setImageResource(R.drawable.unlock)
                    lockMsg.text = "Unlocked"
                }
            }

            cerraduraXdefecto = if (cerraduraXdefecto == 0) 1 else 0
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }

    companion object {
        /**
         * Método de fábrica para crear una nueva instancia del fragment
         */
        @JvmStatic
        fun newInstance() = ControlFragment()
    }
}