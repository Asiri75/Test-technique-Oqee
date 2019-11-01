package com.asiri.traxtest.ui.list

import com.asiri.traxtest.App
import com.asiri.traxtest.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.ref.WeakReference

class MainPresenter(view: MainInterface.View) : MainInterface.Presenter {

    private val view = WeakReference<MainInterface.View>(view)
    private val disposables = CompositeDisposable()

    override fun fetchMovies() {
        disposables.add(
            App.movieRepository.createMoviesServiceCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { apiResponse -> onMoviesCallSuccess(apiResponse) },
                    { throwable -> onMoviesCallError(throwable) }
                ))
    }

    private fun onMoviesCallSuccess(moviesList: List<Movie>?) {
        if (moviesList != null)
            view.get()?.showMovies(moviesList!!)
    }

    private fun onMoviesCallError(throwable: Throwable) {
        view.get()?.showErrorMessage()
        Timber.e(throwable)
    }

    override fun clearDisposable() {
        disposables.clear()
    }

}