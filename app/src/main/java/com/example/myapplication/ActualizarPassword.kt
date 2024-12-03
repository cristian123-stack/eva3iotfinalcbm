package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ActualizarPassword : AppCompatActivity() {

    private lateinit var inputUsuarioPassword: EditText
    private lateinit var inputPasswordAnterior: EditText
    private lateinit var inputNuevaPassword: EditText
    private lateinit var btnActualizarPassword: Button
    private lateinit var btnVolverLoginPassword: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actualizar_password)

        inputUsuarioPassword = findViewById(R.id.inputUsuarioPassword)
        inputPasswordAnterior = findViewById(R.id.inputPasswordAnterior)
        inputNuevaPassword = findViewById(R.id.inputNuevaPassword)
        btnActualizarPassword = findViewById(R.id.btnActualizarPassword)
        btnVolverLoginPassword = findViewById(R.id.btnVolverLoginPassword)

        auth = FirebaseAuth.getInstance()

        btnActualizarPassword.setOnClickListener {
            val usuario = inputUsuarioPassword.text.toString().trim()
            val passwordAnterior = inputPasswordAnterior.text.toString().trim()
            val nuevaPassword = inputNuevaPassword.text.toString().trim()

            if (usuario.isEmpty() || passwordAnterior.isEmpty() || nuevaPassword.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = auth.currentUser
            if (user != null && user.email == usuario) {
                // Reautenticar al usuario
                val credential = EmailAuthProvider.getCredential(usuario, passwordAnterior)
                user.reauthenticate(credential)
                    .addOnCompleteListener { reauthTask ->
                        if (reauthTask.isSuccessful) {
                            // Actualizar la contraseña
                            user.updatePassword(nuevaPassword)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        Toast.makeText(this, "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show()
                                        // Redirigir al login
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Error al actualizar contraseña: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(this, "Contraseña anterior incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error al reautenticar: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Correo electrónico no coincide con el usuario autenticado", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolverLoginPassword.setOnClickListener {
            val intent = Intent(this, OpcionesCuenta::class.java)
            startActivity(intent)
            finish()
        }
    }
}
