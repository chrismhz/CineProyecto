package com.example.cineproyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.cineproyecto.databinding.ActivityMain2Binding
import com.example.cineproyecto.databinding.ActivityMainBinding
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Intent
import android.util.Log

class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main2)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val btnRegresarseCrud: Button = findViewById(R.id.btnRegresarCrud)
        btnRegresarseCrud.setOnClickListener() {
            finish()
        }

        /*------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        binding.btnConsultando.setOnClickListener() {
            var datosList = mutableListOf<String>()
            //var datos = ""
            db.collection("peliculas")
                .get()
                .addOnSuccessListener { resultado ->
                    for (documento in resultado) {
                        //datos += "${documento.id}: ${documento.data}\n"
                        val data = documento.data
                        val id = documento.id
                        val formattedData =
                            "-----------------------------------------------------------------------------------------\n"+
                                    "ID: $id\n"+
                                    "Nombre: ${data["nombre"]},\n" +
                                    "Clasificación: ${data["clasificacion"]},\n" +
                                    "Idioma: ${data["idioma"]},\n" +
                                    "Duración: ${data["duracion"]} min,\n" +
                                    "Año: ${data["anio"]},\n" +
                                    "Género: ${data["genero"]}\n"
                        datosList.add(formattedData)
                    }
                    //binding.tvConsulta.text = datos
                    binding.tvConsulta.text = datosList.joinToString("\n")
                }
                .addOnFailureListener { exception ->
                    binding.tvConsulta.text = "No se ha podido conectar"
                }
        }

        /*------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        binding.btnGuardando.setOnClickListener {
            guardarDatos(db)
        }

        /*------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        binding.btnModificar.setOnClickListener {
            if (binding.edtNombre.text?.isNotBlank() == true &&
                binding.edtClasificacion.text?.isNotBlank() == true &&
                binding.edtIdioma.text?.isNotBlank() == true &&
                binding.edtDuracion.text?.isNotBlank() == true &&
                binding.edtAnio.text?.isNotBlank() == true &&
                binding.edtGenero.text?.isNotBlank() == true &&
                binding.edtId.text?.isNotBlank() == true
            ) {

                guardarDatos(db)

            } else {
                // Mostrar mensajes específicos para campos en blanco
                if (binding.edtNombre.text.isNullOrBlank()) {
                    binding.edtNombre.error = "Ingrese nombre"
                }
                if (binding.edtClasificacion.text.isNullOrBlank()) {
                    binding.edtClasificacion.error = "Ingrese clasificación"
                }
                if (binding.edtIdioma.text.isNullOrBlank()) {
                    binding.edtIdioma.error = "Ingrese idioma"
                }
                if (binding.edtDuracion.text.isNullOrBlank()) {
                    binding.edtDuracion.error = "Ingrese duración"
                }
                if (binding.edtAnio.text.isNullOrBlank()) {
                    binding.edtAnio.error = "Ingrese año"
                }
                if (binding.edtGenero.text.isNullOrBlank()) {
                    binding.edtGenero.error = "Ingrese género"
                }
                if (binding.edtId.text.isNullOrBlank()) {
                    binding.edtId.error = "Ingrese ID"
                }
                binding.tvConsulta.text = "No se actualizo nada. \n¡Por favor Ingrese cada campo!"
            }
        }

        /*------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        binding.btnBorrar.setOnClickListener {
            if (binding.edtId.text?.isNotBlank() == true) {
                db.collection("peliculas")
                    .document(binding.edtId.text.toString())
                    .delete()
                    .addOnSuccessListener { _ ->
                        binding.tvConsulta.text = "Eliminado correctamente"
                    }
                    .addOnFailureListener { _ ->
                        binding.tvConsulta.text = "No se ha podido eliminar"
                    }
            } else {
                binding.tvConsulta.text = "No se inserto id que borrar. \n¡Por favor Ingrese ID!"
            }
        }

    }

    /*------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    private fun guardarDatos(db: FirebaseFirestore) {
        if (binding.edtNombre.text?.isNotBlank() == true &&
            binding.edtClasificacion.text?.isNotBlank() == true &&
            binding.edtIdioma.text?.isNotBlank() == true &&
            binding.edtDuracion.text?.isNotBlank() == true &&
            binding.edtAnio.text?.isNotBlank() == true &&
            binding.edtGenero.text?.isNotBlank() == true &&
            binding.edtId.text?.isNotBlank() == true
        ) {
            // Todos los campos tienen datos válidos
            val dato = hashMapOf(
                //"id" to binding.edtId.text,
                "nombre" to binding.edtNombre.text.toString(),
                "clasificacion" to binding.edtClasificacion.text.toString(),
                "idioma" to binding.edtIdioma.text.toString(),
                "duracion" to binding.edtDuracion.text.toString(),
                "anio" to binding.edtAnio.text.toString(),
                "genero" to binding.edtGenero.text.toString()
            )

            db.collection("peliculas")
                .document(binding.edtId.text.toString())
                .set(dato)
                .addOnSuccessListener { _ ->
                    binding.tvConsulta.text = "Añadido correctamente"
                }
                .addOnFailureListener { _ ->
                    binding.tvConsulta.text = "No se ha podido añadir"
                }
        } else {
            // Mostrar mensajes específicos para campos en blanco
            if (binding.edtNombre.text.isNullOrBlank()) {
                binding.edtNombre.error = "Ingrese nombre"
            }
            if (binding.edtClasificacion.text.isNullOrBlank()) {
                binding.edtClasificacion.error = "Ingrese clasificación"
            }
            if (binding.edtIdioma.text.isNullOrBlank()) {
                binding.edtIdioma.error = "Ingrese idioma"
            }
            if (binding.edtDuracion.text.isNullOrBlank()) {
                binding.edtDuracion.error = "Ingrese duración"
            }
            if (binding.edtAnio.text.isNullOrBlank()) {
                binding.edtAnio.error = "Ingrese año"
            }
            if (binding.edtGenero.text.isNullOrBlank()) {
                binding.edtGenero.error = "Ingrese género"
            }
            if (binding.edtId.text.isNullOrBlank()) {
                binding.edtId.error = "Ingrese ID"
            }
            // Al menos un campo esta en blanco
            binding.tvConsulta.text = "Fallo al guardar registro, \n¡Por favor ingrese en cada campo!"
        }
    }
}