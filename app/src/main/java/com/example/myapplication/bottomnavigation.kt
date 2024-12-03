package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Vistas.AnalisisFragment
import com.example.myapplication.Vistas.ControlFragment
import com.example.myapplication.Vistas.TipsFragment
import com.example.myapplication.databinding.BottomnavigationBinding
import com.google.firebase.auth.FirebaseAuth

class BottomNav : AppCompatActivity() {

    //configurar viewbinding
    private lateinit var binding: BottomnavigationBinding

    //firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = BottomnavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase
        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottomNav)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null){

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TipsFragment()).commit()

        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TipsFragment()).commit()
                    true
                }

                R.id.item_2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AnalisisFragment()).commit()
                    true
                }

                R.id.item_3 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ControlFragment()).commit()
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener {
            when(it.itemId) {
                R.id.item_1 -> {
                    true
                }

                R.id.item_2 -> {
                    true
                }

                R.id.item_3 -> {
                    true
                }
                else -> false
            }
        }

    }
}