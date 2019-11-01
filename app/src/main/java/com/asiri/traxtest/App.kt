package com.asiri.traxtest

import android.app.Application
import com.asiri.traxtest.repository.MovieRepository
import timber.log.Timber

class App : Application(){

    companion object {
        lateinit var movieRepository: MovieRepository
    }

    override fun onCreate() {
        super.onCreate()

        //We initialize the logging library
        Timber.plant(Timber.DebugTree())
        //Same for the album repository
        movieRepository = MovieRepository(this)
    }

}