package com.example.recyclerclase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerclase.databinding.ActivityMain2Binding
import com.example.recyclerclase.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding:ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}