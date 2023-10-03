package com.example.dialogs

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dialogs.databinding.ActivityMainBinding
import java.util.Arrays

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Has pulsado sí", Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Has pulsado no", Toast.LENGTH_SHORT).show()
    }
    val neutralButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Quizá", Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btUno.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("Título")
                setMessage("Mensaje a mostrar")
                setPositiveButton("Aceptar", DialogInterface.OnClickListener(function = positiveButtonClick))
                show()
            }
        }

        binding.btDos.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("Título")
                setMessage("Mensaje a mostrar")
                //Otra forma es definir directamente aquí lo que se hace cuando se pulse.
                setPositiveButton("OK", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                    Toast.makeText(applicationContext,
                        "Has pulsado sí", Toast.LENGTH_SHORT).show()
                }))
                setNegativeButton("No", ({ dialog: DialogInterface, which: Int ->
                    Toast.makeText(applicationContext,
                        "Has pulsado no", Toast.LENGTH_SHORT).show()
                }))
                show()
            }
        }
        binding.btTres.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("Título")
                setMessage("Mensaje a mostrar")
                setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton("No", negativeButtonClick)
                setNeutralButton("Quizá", neutralButtonClick)
                show()
            }
        }

        binding.btMultiple.setOnClickListener {
            val items = arrayOf("Microsoft", "Apple", "Amazon", "Google")
            val selectedList = ArrayList<Int>()
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Varias opciones")
            builder.setMultiChoiceItems(items, null
            ) { dialog, which, isChecked ->
                if (isChecked) {
                    selectedList.add(which)
                } else if (selectedList.contains(which)) {
                    selectedList.remove(Integer.valueOf(which))
                }
            }

            builder.setPositiveButton("Hecho") { dialogInterface, i ->
                val selectedStrings = ArrayList<String>()

                for (j in selectedList.indices) {
                    selectedStrings.add(items[selectedList[j]])
                }

                Toast.makeText(applicationContext, "Items selected are: " + Arrays.toString(selectedStrings.toTypedArray()), Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }

        binding.btEdicion.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle("Con EditText")
            val dialogLayout = inflater.inflate(R.layout.alert_dialog_with_edittext, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { dialogInterface, i -> Toast.makeText(applicationContext, "EditText introducido es " + editText.text.toString(), Toast.LENGTH_SHORT).show() }
            builder.show()
        }

    }
}