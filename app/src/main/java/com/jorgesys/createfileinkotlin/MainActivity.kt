package com.jorgesys.createfileinkotlin

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.io.File

class MainActivity : AppCompatActivity() {


    val REQUEST_PERMISSION = 123
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Requirin permission!")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION)
        } else {
            Log.d(TAG, "You have already permission!")
        }


        val myButton = findViewById<Button>(R.id.btnCreatefile) as Button

        myButton.setOnClickListener {view ->

            writeToFile()

        }


    }


    fun writeToFile() {
        //Define ruta en almacenamiento externo y si deseas un directorio.
        val path = File(Environment.getExternalStorageDirectory(),"/myFiles/")
        var success = true
        //Si el path no existe, trata de crear el directorio.
        if (!path.exists()) {
            success = path.mkdir()
            Log.d(TAG, "Directory " + path + " was created: "  +success)
        }

        //Si el path existe o creo directorio sin problemas ahora crea archivo.
        if (success) {
            Log.d(TAG, "Directory exist, proceed to create the file.")
            val text = "/**\n" +
                    " * We declare a package-level function main which returns Unit and takes\n" +
                    " * an Array of strings as a parameter. Note that semicolons are optional.\n" +
                    " */\n" +
                    "\n" +
                    "fun main(args: Array<String>) {\n" +
                    "    println(\"Hello, world!\")\n" +
                    "}"
            //Write text to file!
            val fileToWrite = File(path,"output.txt").writeText(text)


                    Toast.makeText(this, "A message was written to your file!", Toast.LENGTH_SHORT).show()

        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permissions were accepted!")
            }
        }
    }


}
