package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registro : AppCompatActivity() {

    companion object {
        private const val TAG = "RegistroActivity"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)

        Log.d(TAG, "onCreate: Starting Registration Activity")

        // Inicializar Firebase Auth y Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Referencias a los elementos de la interfaz
        val inputName = findViewById<EditText>(R.id.input_name)
        val inputPassword = findViewById<EditText>(R.id.input_password)
        val botonRegistro = findViewById<Button>(R.id.botonderegistro)
        val atrasBoton = findViewById<Button>(R.id.atrasboton)

        // Botón de registro
        botonRegistro.setOnClickListener {
            Log.d(TAG, "botonRegistro onClick: Button clicked")

            val name = inputName.text.toString()
            val password = inputPassword.text.toString()

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "botonRegistro onClick: Empty fields detected")
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "botonRegistro onClick: Password length less than 6 characters")
                return@setOnClickListener
            }

            // Crear usuario en Firebase Authentication
            auth.createUserWithEmailAndPassword(name, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Registro exitoso
                        Toast.makeText(this, "Registro exitoso. Ahora puedes iniciar sesión :D", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "botonRegistro onSuccess: User registration successful")

                        // Limpiar campos de entrada
                        inputName.text.clear()
                        inputPassword.text.clear()
                    } else {
                        Toast.makeText(this, "Error al registrar usuario: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "botonRegistro onFailure: Error registering user: ${task.exception?.message}", task.exception)
                    }
                }
        }

        // Botón Atrás
        atrasBoton.setOnClickListener {
            auth.signOut() // Cerrar sesión de Firebase
            finish() // Finalizar la actividad actual
        }
    }
}