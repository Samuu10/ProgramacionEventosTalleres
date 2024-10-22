package com.example.pctaller1

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

//Actividad que muestra la pantalla de configuración
class PantallaConfiguracion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_configuracion)

        //Recuperamos el color guardado en SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val backgroundColor = sharedPreferences.getInt("backgroundColor", Color.WHITE)

        //Aplicamos el color al fondo
        val rootView = findViewById<android.view.View>(R.id.rootView)
        rootView.setBackgroundColor(backgroundColor)

        //Definimos los botones
        val buttonChangeBackgroundColor = findViewById<Button>(R.id.buttonChangeBackgroundColor)
        val buttonBackToStart = findViewById<Button>(R.id.buttonBackToStart)

        //Botón para cambiar el color de fondo
        buttonChangeBackgroundColor.setOnClickListener {
            // Cambiamos el color del fondo a un color aleatorio
            val newColor = Color.rgb((0..255).random(), (0..255).random(), (0..255).random())
            rootView.setBackgroundColor(newColor)

            // Guardamos el nuevo color en SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putInt("backgroundColor", newColor)
            editor.apply()
        }

        //Botón para volver a la pantalla de inicio
        buttonBackToStart.setOnClickListener {
            startActivity(Intent(this, PantallaInicio::class.java))
        }
    }
}