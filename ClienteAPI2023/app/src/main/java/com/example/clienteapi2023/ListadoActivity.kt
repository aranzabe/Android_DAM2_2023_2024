package com.example.clienteapi2023

import Adaptadores.MiAdaptadorRV
import Api.MainActivityViewModel
import Api.ServiceBuilder
import Api.UserAPI
import Modelo.Registro
import Modelo.Usuario
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clienteapi2023.databinding.ActivityListadoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoActivity : AppCompatActivity() {
    var datosRepresentar : ArrayList<Registro> = ArrayList()
    lateinit var customAdapter : MiAdaptadorRV
    lateinit var binding: ActivityListadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        binding.RVListaPersonas.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()

        if(operacion.equals("listar")){
            //getUsers()     //--> Con ViewModel
            getUsers2()  //--> Sin ViewModel
        }
        if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString().toInt()
            //getBuscarUnUsuario(idBuscar)      //--> Con ViewModel
            getBuscarUnUsuario2(idBuscar)   //--> Sin ViewModel
        }
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //Api's públicas:
    //https://pokeapi.co/
    //https://randomuser.me/api/
    //https://cataas.com/#/
    //https://jsonplaceholder.typicode.com/posts
    //https://dog.ceo/dog-api/
    //https://rapidapi.com/collection/list-of-free-apis
    //https://apipheny.io/free-api/#apis-without-key
    //https://reqres.in/api/
    //https://gorest.co.in/

    //Bibliografía.
    //https://medium.com/android-news/consuming-rest-api-using-retrofit-library-in-android-ed47aef01ecb
    //https://ed.team/blog/como-consumir-una-api-rest-desde-android
    //https://dev.to/theimpulson/making-get-requests-with-retrofit2-on-android-using-kotlin-4e4c
    //https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter/issues/24
    //https://www.geeksforgeeks.org/how-to-post-data-to-api-using-retrofit-in-android/
    /**
     * Listado de registros usando ViewModel.
     */
    fun getUsers() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getPosts()
        viewModel.myResponseList.observe(this) {

            for (user in it) {
                //Log.d("Fernando", user.userId.toString())
                Log.d("Fernando", user.toString())
                datosRepresentar.add(Registro(user.body.toString(), user.id!!, user.title.toString()))
            }
            customAdapter = MiAdaptadorRV(this@ListadoActivity, datosRepresentar)
            binding.RVListaPersonas.adapter = customAdapter
        }

    }

    //--------------------------------------------------------------------------------------------------------

    //https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
    /**
     * Listado de registros SIN ViewModel.
     */
    fun getUsers2() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUsuarioss()

        call.enqueue(object : Callback<MutableList<Usuario>> {
            override fun onResponse(call: Call<MutableList<Usuario>>, response: Response<MutableList<Usuario>>) {
                Log.e ("Fernando", response.code().toString())
                for (post in response.body()!!) {
                    datosRepresentar.add(Registro(post.body.toString(), post.id!!, post.title.toString()))
                }
                if (response.isSuccessful){
                    binding.RVListaPersonas.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, datosRepresentar)
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Usuario>>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    /**
     * Buscar un registro con ViewModel.
     */
    fun getBuscarUnUsuario(idBusc:Int){
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getPost(idBusc)
        try{
            viewModel.myResponse.observe(this) {
                //if (it?.id != null) {
                if (it != null) {
                    Log.d("Fernando", it.toString())

                    datosRepresentar.add(Registro(it.body.toString(), it.id!!, it.title.toString()))
                    customAdapter = MiAdaptadorRV(this@ListadoActivity, datosRepresentar)
                    binding.RVListaPersonas.adapter = customAdapter
                }
                else {
                    Toast.makeText(this@ListadoActivity, "No encontrado", Toast.LENGTH_SHORT).show()
                    Log.e("Fernando","No encontrado")
                }
            }
        }
        catch (e: Exception){
            Toast.makeText(this@ListadoActivity, "No encontrado", Toast.LENGTH_SHORT).show()
            Log.e("Fernando","No encontrado")
        }

    }



    //https://howtodoinjava.com/retrofit2/query-path-parameters/
    /**
     * Buscar un registro SIN ViewModel.
     */
    fun getBuscarUnUsuario2(idBusc:Int){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnUsuario(idBusc);

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                val post = response.body()
                if (post != null) {
                    datosRepresentar.add(Registro(post.body.toString(), post.id!!, post.title.toString()))
                }
                if (response.isSuccessful){
                    binding.RVListaPersonas.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, datosRepresentar)
                    }
                }
                else {
                    Toast.makeText(this@ListadoActivity, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}