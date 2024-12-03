package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.OpcionesCuentaBinding

class OpcionesCuenta : AppCompatActivity() {

    private lateinit var binding: OpcionesCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OpcionesCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnActualizarUsuario.setOnClickListener {
            val intent = Intent(this, ActualizarUsuario::class.java)
            startActivity(intent)
        }

        binding.btnActualizarPassword.setOnClickListener {
            val intent = Intent(this, ActualizarPassword::class.java)
            startActivity(intent)
        }

        binding.btnEliminarCuenta.setOnClickListener {
            val intent = Intent(this, EliminarCuenta::class.java)
            startActivity(intent)
        }

        binding.btnVolverLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
