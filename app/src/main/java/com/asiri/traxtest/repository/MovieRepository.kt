package com.asiri.traxtest.repository

import android.content.Context
import com.asiri.lbctest.repository.MovieApi
import com.asiri.traxtest.model.Movie
import com.asiri.traxtest.repository.helper.HTTPHelper
import com.asiri.traxtest.repository.helper.HTTPHelper.initSSL
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MovieRepository (context: Context){

    private val retrofit: Retrofit
    private val MOVIES_URL = "https://testepg.r0ro.fr/"

    /**
     * We create a custom deserializer to get only the useful informations for the test
     */
    private var customDeserializer: JsonDeserializer<Movie> =
        JsonDeserializer { json, _, _ ->
            val jsonObject = json.asJsonObject
            Movie(
                //For the title of the movie
                jsonObject.get("page").asJsonObject.get("movie_title").asString,
                //For the cover picture
                jsonObject.get("heros").asJsonObject.get("0").asJsonObject.get("imageurl").asString,
                //For the trailer URL (we take only the sd version #KeepItSimple)
                jsonObject.get("clips").asJsonArray.get(0).asJsonObject
                    .get("versions").asJsonObject
                    .get("enus").asJsonObject
                    .get("sizes").asJsonObject
                    .get("sd").asJsonObject
                    .get("src").asString
            )
        }


    init {
        //Init the http components
        val httpClientBuilder= OkHttpClient.Builder()
        val gsonBuilder =  GsonBuilder()
        //Attach the custom deserializer
        gsonBuilder.registerTypeAdapter(Movie::class.java, customDeserializer)

        //Add the certification authority
        initSSL(context, httpClientBuilder)

        //Building the Retrofit client
        retrofit = Retrofit.Builder()
            .baseUrl(MOVIES_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .client(httpClientBuilder.build())
            .build()
    }

    /**
     * Function to call the movies api
     */
    fun syncMovieNow() {
        //Prepare and execute the service
        val service = retrofit.create(MovieApi::class.java)
        service.getMovies().enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                Timber.d("We got a valid response from the movies api")
                val movies = response.body()
                if (movies != null) {
                    for(movie in movies) {
                        Timber.d("good, movie: ${movie.title}, ${movie.coverUrl}, ${movie.trailerUrl}")
                    }
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Timber.e("HTTP GET fail")
                Timber.e(t)
            }
        })
        Timber.d("Executing an movie HTTP GET")
    }

}