package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.RecylerViewAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.CryptoModel
import com.example.myapplication.service.CryptoApi
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),RecylerViewAdapter.Listener {

    private val BASE_URL="https://raw.githubusercontent.com/"
    private var cryptoModels:ArrayList<CryptoModel>?=null
    private var recylerViewAdapter:RecylerViewAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        var layoutManager:RecyclerView.LayoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager

        loadData()

    }

    private fun loadData(){

        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service=retrofit.create(CryptoApi::class.java)
        var call=service.getData()

        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        cryptoModels=ArrayList(it)

                        cryptoModels?.let{
                            recylerViewAdapter=RecylerViewAdapter(cryptoModels!!,this@MainActivity)
                            recyclerView.adapter=recylerViewAdapter
                        }

                /*
                        for (cryptoModell:CryptoModel in cryptoModels!!){
                            println(cryptoModell.currency)
                            println(cryptoModell.price)
                        }
                        */

                    }

                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })



    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Tıklandı : ${cryptoModel.currency}",Toast.LENGTH_SHORT).show()
    }
}