package com.asiri.traxtest.ui.movie

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiri.traxtest.R
import com.asiri.traxtest.model.Movie
import com.asiri.traxtest.ui.list.MainActivity.Companion.MOVIE_EXTRA
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsInterface.View {

    private lateinit var presenter: MovieDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        presenter = MovieDetailsPresenter(this)
        val movie = intent.getSerializableExtra(MOVIE_EXTRA) as Movie

        initToolbar(movie)
        initPlayer(movie)
    }

    private fun initToolbar(movie: Movie) {
        supportActionBar?.title = movie.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initPlayer(movie: Movie) {
        movie_view.player = presenter.getPlayer().getPlayerImpl(this)
        presenter.play(movie.trailerUrl)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            presenter.releasePlayer()
        }
    }



    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            presenter.releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setMediaSessionState(false)
    }
}
