package com.example.movies.fragments

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.movies.network.MoviesApi
import com.example.movies.network.Okay
import com.example.movies.network.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsFragmentViewModel : ViewModel() {
    private val _text = MutableLiveData<String>()
    val text : LiveData<String>
        get() = _text

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        _text.value = "fetching"
        getMovies()
    }

//    private fun getMovies(){
//        MoviesApi.retrofitService.getMovies().enqueue(object : Callback<Okay>{
//            override fun onResponse(call: Call<Okay>, response: Response<Okay>) {
//                val okay: Okay? = response.body()
//                val result: List<Results>? = okay?.results
//                _text.value = result?.get(0)?.title
//            }
//
//            override fun onFailure(call: Call<Okay>, t: Throwable) {
//                _text.value = "Failure" + t.message
//            }
//
//        })
//    }

    private fun getMovies(){
        coroutineScope.launch {
            var getPropertiesDeferred = MoviesApi.retrofitService.getMovies()
            try {
                var response: Okay = getPropertiesDeferred.await()
                val results : List<Results> = response.results
                _text.value = "${results[0].title}"
            } catch (e: Exception) {
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}