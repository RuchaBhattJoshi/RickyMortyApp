package com.example.rickmortyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickmortyapp.Character
import com.google.android.material.snackbar.Snackbar

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
        state-> processCharacterResponse(state)
        })

    }

    private fun processCharacterResponse(state:ScreenState<List<Character>?>){

        val pb = findViewById<ProgressBar>(R.id.progressbar)

        when(state){
            is ScreenState.Loading ->{
             pb.visibility = View.VISIBLE
            }

            is ScreenState.Success ->{
                pb.visibility = View.GONE
                if(state.data!=null){
                    val adapter = RecyclerViewAdapter(state.data)
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView?.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                    recyclerView?.adapter =adapter
                }
            }

            is ScreenState.Error -> {
                pb.visibility = View.GONE
                val view = pb.rootView
                Snackbar.make(view,state.message!!,Snackbar.LENGTH_LONG).show()
            }
        }
    }
}