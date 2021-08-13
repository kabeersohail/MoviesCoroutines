package com.example.movies.network

data class Okay (

    val page : Int,
    val results : List<Results>,
    val total_pages : Int,
    val total_results : Int
)
