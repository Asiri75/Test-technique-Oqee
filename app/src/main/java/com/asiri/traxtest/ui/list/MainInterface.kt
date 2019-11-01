package com.asiri.traxtest.ui.list

import com.asiri.traxtest.model.Movie

interface MainInterface {
    interface Presenter {

        fun fetchMovies()

        fun clearDisposable()

    }

    interface View {

        fun showMovies(movies: List<Movie>)

        fun showErrorMessage()
    }
}