package com.example.cineproyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class resultado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        // Obtener el resultado del Intent
        val resultado = intent.getStringExtra("resultado")

        // Mostrar el resultado en el TextView de activity_resultado.xml
        val tvResultado: TextView = findViewById(R.id.tvResultado)
        tvResultado.text = resultado

        val btnRegresarseCrud: Button = findViewById(R.id.btnRegresarItem)
        btnRegresarseCrud.setOnClickListener(){
            finish()
        }
    }
}