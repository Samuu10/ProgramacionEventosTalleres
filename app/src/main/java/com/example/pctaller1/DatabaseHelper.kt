package com.example.pctaller1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Clase para manejar la base de datos SQLite
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //Constantes para la base de datos
    companion object {
        private const val DATABASE_NAME = "personInfo.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Person"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_HEIGHT = "height"
        private const val COLUMN_WEIGHT = "weight"
    }

    //Creamos la tabla en la base de datos
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_NAME TEXT,"
                + "$COLUMN_AGE INTEGER,"
                + "$COLUMN_HEIGHT INTEGER,"
                + "$COLUMN_WEIGHT INTEGER)")
        db.execSQL(createTable)
    }

    //Método para actualizar la base de datos
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //Método para insertar una persona en la base de datos
    fun insertPerson(name: String, age: Int, height: Int, weight: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_AGE, age)
        contentValues.put(COLUMN_HEIGHT, height)
        contentValues.put(COLUMN_WEIGHT, weight)
        return db.insert(TABLE_NAME, null, contentValues)
    }

    //Método para obtener la información de una persona
    fun getPersonInfo(name: String): Cursor {
        val db = this.readableDatabase
        return db.query(TABLE_NAME, null, "$COLUMN_NAME=?", arrayOf(name), null, null, null)
    }
}