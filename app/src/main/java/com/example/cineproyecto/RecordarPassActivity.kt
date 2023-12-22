package com.example.cineproyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecordarPassActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordar_pass)

        val txtmail : TextView = findViewById(R.id.txtEmailCambio)
        val btnCambiar : Button = findViewById(R.id.btnCambiar)
        val btnRegresarR : Button = findViewById(R.id.btnRegresarRecuperar)

        btnCambiar.setOnClickListener()
        {
            val cambiazo = txtmail.text.toString()

            if(cambiazo.trim().isEmpty()){
                txtmail.setError("Ingrese correo electronico")
                return@setOnClickListener
            }

            sendPasswordReset(txtmail.text.toString())
        }
        btnRegresarR.setOnClickListener()
        {
            finish()
        }

        firebaseAuth = Firebase.auth
    }

    private fun sendPasswordReset (email: String)
    {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(){ task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(baseContext, "Cambio de Contraseña Enviado al correo", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(baseContext, "Error, no se pudo complementar el proceso", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
