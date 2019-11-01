package com.asiri.traxtest.ui.movie

import com.asiri.traxtest.domain.movieplayer.MoviePlayer
import java.lang.ref.WeakReference

class MovieDetailsPresenter(movieDetailsView: MovieDetailsInterface.View) : MovieDetailsInterface.Presenter {
    private val view = WeakReference(movieDetailsView)

    private val mediaPlayer = MoviePlayer()

    override fun getPlayer() = mediaPlayer

    override fun play(url: String) = mediaPlayer.play(url)

    override fun releasePlayer() = mediaPlayer.releasePlayer()

    override fun setMediaSessionState(isActive: Boolean) {
        mediaPlayer.setMediaSessionState(isActive)
    }
}