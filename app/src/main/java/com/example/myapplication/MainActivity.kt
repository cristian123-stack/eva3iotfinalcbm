package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.botoniniciar.setOnClickListener {
            val email = binding.inputUsuario.text.toString()
            val contraseña = binding.inputContrasena.text.toString()
            if (!validarCampos(email, contraseña)) return@setOnClickListener
            signIn(email, contraseña)
        }

        binding.botonregistro.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            abrirDashboard()
        }
    }

    private fun validarCampos(email: String, contraseña: String): Boolean {
        var esValido = true
        if (email.isEmpty()) {
            binding.inputUsuario.error = "Por favor ingrese un email"
            esValido = false
        }
        if (contraseña.isEmpty()) {
            binding.inputContrasena.error = "Por favor ingrese una contraseña"
            esValido = false
        }
        return esValido
    }

    private fun signIn(email: String, contraseña: String) {
        try {
            auth.signInWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                        abrirDashboard()
                    } else {
                        val errorCode = (task.exception as? FirebaseAuthException)?.errorCode
                        when (errorCode) {
                            "ERROR_INVALID_EMAIL" -> Toast.makeText(
                                this,
                                "El email es inválido",
                                Toast.LENGTH_SHORT
                            ).show()

                            "ERROR_WRONG_PASSWORD" -> Toast.makeText(
                                this,
                                "Contraseña incorrecta",
                                Toast.LENGTH_SHORT
                            ).show()

                            "ERROR_USER_NOT_FOUND" -> Toast.makeText(
                                this,
                                "Usuario no registrado",
                                Toast.LENGTH_SHORT
                            ).show()

                            else -> Toast.makeText(
                                this,
                                "Error: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al iniciar sesión: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirDashboard() {
        val intent = Intent(this, dashboard::class.java)
        startActivity(intent)
        finish()
    }
}