package com.example.pctaller1

import android.annotation.SuppressLint
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
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

//Actividad principal de la aplicación
class ActividadPrincipal : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actividad_principal)

        //Definimos las vistas
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val buttonSaveName = findViewById<Button>(R.id.buttonSaveName)
        val textViewDisplayName = findViewById<TextView>(R.id.textViewDisplayName)
        val buttonGoToSettings = findViewById<Button>(R.id.buttonGoToSettings)
        progressBar = findViewById(R.id.progressBar)
        val buttonSaveInfo = findViewById<Button>(R.id.buttonSaveInfo)
        val buttonLoadInfo = findViewById<Button>(R.id.buttonLoadInfo)

        //Recuperamos el color guardado en SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val backgroundColor = sharedPreferences.getInt("backgroundColor", Color.WHITE)

        //Aplicamos el color al fondo
        val rootView = findViewById<android.view.View>(R.id.rootView)
        rootView.setBackgroundColor(backgroundColor)

        //Botón para guardar el nombre en SharedPreferences
        buttonSaveName.setOnClickListener {
            val name = editTextName.text.toString()
            //Guardamos el nombre en SharedPreferences si no está vacío
            if (name.isNotBlank()) {
                val editor = sharedPreferences.edit()
                editor.putString("userName", name)
                editor.apply()
                textViewDisplayName.text = name
                Toast.makeText(this, "Nombre guardado", Toast.LENGTH_SHORT).show()
            //Mostramos un mensaje si el nombre está vacío
            } else {
                Toast.makeText(this, "Por favor, ingresa un nombre", Toast.LENGTH_SHORT).show()
            }
        }

        //Botón para ir a la pantalla de configuración
        buttonGoToSettings.setOnClickListener {
            startActivity(Intent(this, PantallaConfiguracion::class.java))
        }

        //Botón para iniciar la tarea en segundo plano
        val buttonStartTask = findViewById<Button>(R.id.buttonStartTask)
        buttonStartTask.setOnClickListener {
            val name = editTextName.text.toString()
            if (name.isNotBlank()) {
                progressBar.visibility = ProgressBar.VISIBLE
                //Iniciar AsyncTask para simular una operación de red
                BackgroundTask().execute(name)
            } else {
                Toast.makeText(this, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show()
            }
        }

        //Botón para guardar la información de la persona
        buttonSaveInfo.setOnClickListener {
            val name = editTextName.text.toString()
            //Mostramos un diálogo para introducir la información de la persona
            if (name.isNotBlank()) {
                showPersonInfoDialog(this) { age, height, weight ->
                    //La información se guarda en la base de datos en un hilo secundario
                    Thread {
                        val dbHelper = DatabaseHelper(this)
                        //Insertamos la información de la persona en la base de datos
                        dbHelper.insertPerson(name, age, height, weight)
                        //Mostramos un mensaje al usuario en el hilo principal
                        runOnUiThread {
                            Toast.makeText(this, "Información guardada", Toast.LENGTH_SHORT).show()
                        }
                    }.start()
                }
            //Mostramos un mensaje si el nombre está vacío
            } else {
                Toast.makeText(this, "Por favor, ingresa un nombre", Toast.LENGTH_SHORT).show()
            }
        }

        //Botón para cargar la información de la persona
        buttonLoadInfo.setOnClickListener {
            val name = editTextName.text.toString()
            //Obtenemos la información de la persona en un hilo secundario
            if (name.isNotBlank()) {
                //La información se obtiene de la base de datos en un hilo secundario
                Thread {
                    val dbHelper = DatabaseHelper(this)
                    val cursor = dbHelper.getPersonInfo(name)
                    //Mostramos la información si se encontró en la base de datos
                    if (cursor.moveToFirst()) {
                        val age = cursor.getInt(cursor.getColumnIndex("age"))
                        val height = cursor.getInt(cursor.getColumnIndex("height"))
                        val weight = cursor.getInt(cursor.getColumnIndex("weight"))
                        //Mostramos un diálogo con la información de la persona
                        runOnUiThread {
                            AlertDialog.Builder(this)
                                .setTitle("Información de $name")
                                .setMessage("Edad: $age\nAltura: $height cm\nPeso: $weight kg")
                                .setPositiveButton("OK", null)
                                .show()
                        }
                    //Mostramos un mensaje si no se encontró información
                    } else {
                        runOnUiThread {
                            Toast.makeText(this, "No se encontró información para $name", Toast.LENGTH_SHORT).show()
                        }
                    }
                    cursor.close()
                }.start()
            //Mostramos un mensaje si el nombre está vacío
            } else {
                Toast.makeText(this, "Por favor, ingresa un nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Clase interna que implementa AsyncTask para la tarea en segundo plano
    private inner class BackgroundTask : AsyncTask<String, Int, String>() {
        //Método que se ejecuta en segundo plano y simula una operación de red
        override fun doInBackground(vararg params: String?): String {
            val name = params[0]
            //Simulamos una operación de red con un retraso
            for (i in 1..10) {
                //Simulamos un retraso de 500 ms
                Thread.sleep(500)
                publishProgress(i * 10)
            }
            return "Operación completada para $name"
        }

        //Método que se ejecuta en el hilo principal y actualiza la barra de progreso
        override fun onProgressUpdate(vararg values: Int?) {
            progressBar.progress = values[0] ?: 0
        }

        //Método que se ejecuta en el hilo principal y muestra un mensaje al usuario
        override fun onPostExecute(result: String?) {
            progressBar.visibility = ProgressBar.GONE
            Toast.makeText(this@ActividadPrincipal, result, Toast.LENGTH_SHORT).show()
        }
    }

    //Función para mostrar un diálogo para introducir la información de la persona
    fun showPersonInfoDialog(context: Context, onSave: (age: Int, height: Int, weight: Int) -> Unit) {
        //Definimos la vista del diálogo
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)
        val editTextAge = dialogView.findViewById<EditText>(R.id.editTextAge)
        val editTextHeight = dialogView.findViewById<EditText>(R.id.editTextHeight)
        val editTextWeight = dialogView.findViewById<EditText>(R.id.editTextWeight)

        //Mostramos el diálogo
        AlertDialog.Builder(context)
            .setTitle("Introduce la información")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val age = editTextAge.text.toString().toInt()
                val height = editTextHeight.text.toString().toInt()
                val weight = editTextWeight.text.toString().toInt()
                onSave(age, height, weight)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}