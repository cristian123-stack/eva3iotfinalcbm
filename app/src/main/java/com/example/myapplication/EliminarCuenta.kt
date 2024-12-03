package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class EliminarCuenta : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.eliminar_cuenta)

        val inputCorreoEliminar = findViewById<EditText>(R.id.inputUsuarioEliminar)
        val inputPasswordEliminar = findViewById<EditText>(R.id.inputPasswordEliminar)
        val btnConfirmarEliminarCuenta = findViewById<Button>(R.id.btnConfirmarEliminarCuenta)
        val btnVolverMenuAnterior = findViewById<Button>(R.id.btnVolverLoginEliminar)

        auth = FirebaseAuth.getInstance()

        btnConfirmarEliminarCuenta.setOnClickListener {
            val correo = inputCorreoEliminar.text.toString().trim()
            val password = inputPasswordEliminar.text.toString().trim()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val credential = EmailAuthProvider.getCredential(correo, password)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        // Obtener el usuario autenticado
                        val user = auth.currentUser
                        user?.let {
                            // Eliminar la cuenta del usuario autenticado
                            user.delete()
                                .addOnCompleteListener { deleteTask ->
                                    if (deleteTask.isSuccessful) {
                                        Toast.makeText(this, "Cuenta eliminada correctamente", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Error al eliminar la cuenta: ${deleteTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                    } else {
                        Toast.makeText(this, "Error de autenticación: ${authTask.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        btnVolverMenuAnterior.setOnClickListener {
            val intent = Intent(this, OpcionesCuenta::class.java)
            startActivity(intent)
            finish()
        }
    }
}


