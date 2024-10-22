package com.example.pctaller1

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

//Actividad que muestra la pantalla de inicio
class PantallaInicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_inicio)

        //Definimos las vistas
        val textViewGreeting = findViewById<TextView>(R.id.textViewGreeting)
        val buttonGoToMain = findViewById<Button>(R.id.buttonGoToMain)

        //Recuperamos el color guardado en SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val backgroundColor = sharedPreferences.getInt("backgroundColor", Color.WHITE)

        //Aplicamos el color al fondo
        val rootView = findViewById<android.view.View>(R.id.rootView)
        rootView.setBackgroundColor(backgroundColor)

        //Recuperamos el nombre guardado en SharedPreferences
        val userName = sharedPreferences.getString("userName", "")

        //Iniciamos AsyncTask para obtener el saludo
        ObtenerSaludoTask(textViewGreeting, userName).execute()

        //Botón para ir a la pantalla principal
        buttonGoToMain.setOnClickListener {
            startActivity(Intent(this, ActividadPrincipal::class.java))
        }
    }

    //Clase interna que implementa AsyncTask para obtener el saludo
    private class ObtenerSaludoTask(private val textView: TextView, private val userName: String?) : AsyncTask<Void, Void, String>() {
        //Método que se ejecuta en segundo plano y obtiene el saludo personalizado según la hora actual
        override fun doInBackground(vararg params: Void?): String {
            //Obtemos la hora actual
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val greeting = when {
                currentHour in 0..11 -> "Buenos días"
                currentHour in 12..19 -> "Buenas tardes"
                else -> "Buenas noches"
            }
            //Retornamos el saludo personalizado según la hora y el nombre
            return if (userName.isNullOrBlank()) greeting else "$greeting, $userName"
        }

        //Método que se ejecuta en el hilo principal y actualiza el TextView con el saludo
        override fun onPostExecute(result: String) {
            textView.text = result
        }
    }
}