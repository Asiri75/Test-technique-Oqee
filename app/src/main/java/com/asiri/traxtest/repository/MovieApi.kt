package com.asiri.lbctest.repository

import com.asiri.traxtest.model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {

    @GET("movies.json")
    fun getMovies(): Call<List<Movie>>
}