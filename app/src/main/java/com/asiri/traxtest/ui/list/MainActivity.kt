package com.asiri.traxtest.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.asiri.traxtest.R
import com.asiri.traxtest.model.Movie
import com.asiri.traxtest.ui.movie.MovieDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MoviesListAdapter.MoviesListAdapterListener, MainInterface.View {

    private lateinit var presenter: MainPresenter
    private lateinit var moviesListAdapter: MoviesListAdapter
    private var movieList: ArrayList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        movies_list.setHasFixedSize(true)
        moviesListAdapter = MoviesListAdapter(this, movieList,this)
        movies_list.adapter = moviesListAdapter
    }

    override fun onStart() {
        super.onStart()
        presenter.fetchMovies()
        progress_bar.visibility = View.VISIBLE
    }

    override fun showMovies(movies: List<Movie>) {
        progress_bar.visibility = View.GONE
        movieList.clear()
        movieList.addAll(movies)
        moviesListAdapter.notifyDataSetChanged()
    }

    override fun showErrorMessage() {
        progress_bar.visibility = View.GONE
        movies_error.visibility = View.VISIBLE
    }

    override fun onMovieSelected(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_EXTRA, movie)
        startActivity(intent)
    }

    companion object  {
        const val MOVIE_EXTRA = "MOVIE_EXTRA"
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearDisposable()
    }
}
