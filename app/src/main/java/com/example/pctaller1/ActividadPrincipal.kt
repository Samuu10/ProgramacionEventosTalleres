package com.example.pctaller1

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActividadPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_principal)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val buttonSaveName = findViewById<Button>(R.id.buttonSaveName)
        val textViewDisplayName = findViewById<TextView>(R.id.textViewDisplayName)
        val buttonGoToSettings = findViewById<Button>(R.id.buttonGoToSettings)

        //Recuperamos el color guardado en SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val backgroundColor = sharedPreferences.getInt("backgroundColor", Color.WHITE)

        //Aplicamos el color al fondo
        val rootView = findViewById<android.view.View>(R.id.rootView)
        rootView.setBackgroundColor(backgroundColor)

        buttonSaveName.setOnClickListener {
            val name = editTextName.text.toString()
            textViewDisplayName.text = "Hola, $name"
        }

        buttonGoToSettings.setOnClickListener {
            startActivity(Intent(this, PantallaConfiguracion::class.java))
        }
    }
}