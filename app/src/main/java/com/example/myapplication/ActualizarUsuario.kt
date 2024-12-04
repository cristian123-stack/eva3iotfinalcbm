package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ActualizarUsuario : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actualizar_usuario)

        val inputCorreoAnterior = findViewById<EditText>(R.id.inputUsuarioAnterior)
        val inputNuevoCorreo = findViewById<EditText>(R.id.inputNuevoUsuario)
        val inputPassword = findViewById<EditText>(R.id.input_contrasena)
        val btnActualizarCorreo = findViewById<Button>(R.id.btnActualizarUsuario)
        val btnVolverMenu = findViewById<Button>(R.id.btnVolverLogin)

        auth = FirebaseAuth.getInstance()

        btnActualizarCorreo.setOnClickListener {
            val correoAnterior = inputCorreoAnterior.text.toString().trim()
            val nuevoCorreo = inputNuevoCorreo.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            if (correoAnterior.isEmpty() || nuevoCorreo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUser = auth.currentUser
            if (currentUser?.email == correoAnterior) {
                // Reautenticar al usuario con las credenciales actuales
                val credential = EmailAuthProvider.getCredential(correoAnterior, password)
                currentUser.reauthenticate(credential)
                    .addOnCompleteListener { reauthTask ->
                        if (reauthTask.isSuccessful) {
                            // Actualizar el correo en Firebase Authentication
                            currentUser.updateEmail(nuevoCorreo)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        Toast.makeText(this, "Correo actualizado correctamente", Toast.LENGTH_SHORT).show()
                                        // Cerrar sesión para que el usuario vuelva a iniciar sesión con el nuevo correo
                                        auth.signOut()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Error al actualizar el correo: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(this, "Error de reautenticación: ${reauthTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error al autenticar: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "El correo ingresado no coincide con el actual", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolverMenu.setOnClickListener {
            val intent = Intent(this, OpcionesCuenta::class.java)
            startActivity(intent)
            finish()
        }
    }
}

