package com.example.cineproyecto

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btningresar : Button = findViewById(R.id.btnIngresar)
        val txtemail : TextView = findViewById(R.id.edtEmail)
        val txtpass : TextView = findViewById(R.id.edtPassword)
        val btnCrear_CuentaNueva : TextView = findViewById(R.id.btnCrearCuenta)
        val btnRecordar : TextView = findViewById(R.id.btnOlvidar)

        firebaseAuth = Firebase.auth

        btningresar.setOnClickListener()
        {
            val email = txtemail.text.toString()
            val password = txtpass.text.toString()

            if (email.trim().isEmpty()){
                txtemail.setError("Ingrese correo electronico")
                return@setOnClickListener
            }

            if (password.trim().isEmpty()){
                txtpass.setError("Ingrese contraseña")
                return@setOnClickListener
            }

            signIn(txtemail.text.toString(),txtpass.text.toString())
        }

        btnCrear_CuentaNueva.setOnClickListener()
        {
            val i = Intent(this, CrearCuentaActivity::class.java)
            startActivity(i)
        }

        btnRecordar.setOnClickListener()
        {
            val i = Intent(this,RecordarPassActivity::class.java)
            startActivity(i)
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun signIn(email: String, password: String)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
            if (task.isSuccessful){
                val user = firebaseAuth.currentUser
                val verifica = user?.isEmailVerified
                    if(verifica==true)
                    {
                        Toast.makeText(baseContext, "¡Bienvenido!", Toast.LENGTH_LONG).show()
                        //aqui vamos a ir a la segunda activity
                        val i = Intent(this, MainActivity2::class.java)
                        startActivity(i)
                    }
                    else
                    {
                        Toast.makeText(baseContext, "No ha verificado su correo", Toast.LENGTH_SHORT).show()
                    }
            }
            else{
                Toast.makeText(baseContext,"Correo o Contrasenia incorrectos", Toast.LENGTH_LONG).show()
            }
        }
    }
}