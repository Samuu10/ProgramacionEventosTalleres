package com.example.pctaller1

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class PantallaInicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_inicio)

        val textViewGreeting = findViewById<TextView>(R.id.textViewGreeting)
        val buttonGoToMain = findViewById<Button>(R.id.buttonGoToMain)

        //Recuperamos el color guardado en SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val backgroundColor = sharedPreferences.getInt("backgroundColor", Color.WHITE)

        //Aplicamos el color al fondo
        val rootView = findViewById<android.view.View>(R.id.rootView)
        rootView.setBackgroundColor(backgroundColor)

        //Lógica para el saludo según la hora del día
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        textViewGreeting.text = when {
            currentHour in 0..11 -> "Buenos días"
            currentHour in 12..19 -> "Buenas tardes"
            else -> "Buenas noches"
        }

        buttonGoToMain.setOnClickListener {
            startActivity(Intent(this, ActividadPrincipal::class.java))
        }
    }
}