package com.example.cineproyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CrearCuentaActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)
        val txtnombre_nuevo : TextView = findViewById(R.id.edtNombre)
        val txtemail_nuevo : TextView = findViewById(R.id.edtEmailNuevo)
        val txtpassword1 : TextView = findViewById(R.id.edtPasswordNuevo1)
        val txtpassword2 : TextView = findViewById(R.id.edtPasswordNuevo2)
        val txtNombre: TextView = findViewById(R.id.edtNombre)
        val txtEmail: TextView = findViewById(R.id.edtEmailNuevo)
        val txtPassword1: TextView = findViewById(R.id.edtPasswordNuevo1)
        val txtPassword2: TextView = findViewById(R.id.edtPasswordNuevo2)
        val btnCrear : TextView = findViewById(R.id.btnCrearCuentaNuevo)
        val btnRegresarCuenta : Button = findViewById(R.id.btnRegresarNuevo)

        btnCrear.setOnClickListener {
            val name = txtnombre_nuevo.text.toString()
            val email = txtemail_nuevo.text.toString()
            val contrasenia = txtpassword1.text.toString()
            val confirmar = txtpassword2.text.toString()

            if (name.trim().isEmpty()) {
                txtNombre.error = "Ingrese nombre"
                txtNombre.requestFocus()
                return@setOnClickListener
            }

            if (email.trim().isEmpty()) {
                txtEmail.error = "Ingrese correo electrónico"
                txtEmail.requestFocus()
                return@setOnClickListener
            }

            if (contrasenia.trim().isEmpty()) {
                txtPassword1.error = "Ingrese contraseña"
                txtPassword1.requestFocus()
                return@setOnClickListener
            }

            if (confirmar.trim().isEmpty()) {
                txtPassword2.error = "Confirme contraseña"
                txtPassword2.requestFocus()
                return@setOnClickListener
            }

            if (contrasenia != confirmar) {
                Toast.makeText(baseContext, "Error: las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                txtPassword1.requestFocus()
                return@setOnClickListener
            }

            // Luego de pasar todas las validaciones, crear la cuenta
            createAccount(email, contrasenia)
        }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        btnRegresarCuenta.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la actividad anterior
        }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        firebaseAuth = Firebase.auth

    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    private fun createAccount(email: String, password:String){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful) {
                    sendEmailVerification();
                    Toast.makeText(baseContext, "Cuenta creada Correctamente, \n Verifique su correo", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Algo Salio mal, Error" + task.exception, Toast.LENGTH_SHORT).show()
                }
            }
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    private fun sendEmailVerification() {
        val user = firebaseAuth.currentUser!!
        user?.sendEmailVerification()?.addOnCompleteListener(this){task->
            if (task.isSuccessful) {

            } else {

            }
        }
    }
}