package com.example.rickmortyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class MainActivity : AppCompatActivity() {

    //only once it needed
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //owner and lambda as parameter in observer
        viewModel.characterLiveData.observe(this,{
            characters ->
            val adapter = RecyclerViewAdapter(characters)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView?.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            recyclerView?.adapter =adapter
        })

//        val client = ApiClient.apiService.fetchCharacters("1")
//
//        client.enqueue(object : retrofit2.Callback<CharacterResponse> {
//            override fun onResponse(
//                call: Call<CharacterResponse>,
//                response: Response<CharacterResponse>
//            ) {
//                if (response.isSuccessful) {
//                    Log.d("characters", " " + response.body())
//
//                    val result = response.body()?.result
//                    result?.let {
//                        val adapter = RecyclerViewAdapter(result)
//                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//                        recyclerView?.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
//                        recyclerView?.adapter =adapter
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
//                Log.d("error:", " " +t.message)
//            }
//
//
//        })


    }
}