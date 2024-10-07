package com.example.pctaller1

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActividadPrincipal : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_principal)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val buttonSaveName = findViewById<Button>(R.id.buttonSaveName)
        val textViewDisplayName = findViewById<TextView>(R.id.textViewDisplayName)
        val buttonGoToSettings = findViewById<Button>(R.id.buttonGoToSettings)
        progressBar = findViewById(R.id.progressBar) // Añadir ProgressBar al layout

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

        //Botón para iniciar la tarea en segundo plano
        val buttonStartTask = findViewById<Button>(R.id.buttonStartTask)
        buttonStartTask.setOnClickListener {
            val name = editTextName.text.toString()
            if (name.isNotBlank()) {
                progressBar.visibility = ProgressBar.VISIBLE
                // Iniciar AsyncTask
                BackgroundTask().execute(name)
            } else {
                Toast.makeText(this, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Clase interna que implementa AsyncTask para la tarea en segundo plano
    private inner class BackgroundTask : AsyncTask<String, Int, String>() {
        //Método que se ejecuta en segundo plano
        override fun doInBackground(vararg params: String?): String {
            val name = params[0]
            for (i in 1..10) {
                //Simulamos una operación de red con un retraso
                Thread.sleep(500)
                publishProgress(i * 10)
            }
            return "Operación completada para $name"
        }

        //Método que se ejecuta en el hilo principal y actualiza la barra de progreso
        override fun onProgressUpdate(vararg values: Int?) {
            progressBar.progress = values[0] ?: 0
        }

        //Método que se ejecuta en el hilo principal y muestra el resultado
        override fun onPostExecute(result: String?) {
            progressBar.visibility = ProgressBar.GONE
            Toast.makeText(this@ActividadPrincipal, result, Toast.LENGTH_SHORT).show()
        }
    }
}
