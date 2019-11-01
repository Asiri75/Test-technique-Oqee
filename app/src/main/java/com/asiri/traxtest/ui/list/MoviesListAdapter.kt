package com.asiri.traxtest.ui.list

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.asiri.traxtest.R
import com.asiri.traxtest.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class MoviesListAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    private val listener: MoviesListAdapterListener?
) : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>(), View.OnClickListener {


    interface MoviesListAdapterListener {
        fun onMovieSelected(movie: Movie)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieView = itemView.findViewById<ConstraintLayout>(R.id.movie_view)!!
        val movieImage = itemView.findViewById<ImageView>(R.id.movie_picture)!!
        val movieTitle = itemView.findViewById<TextView>(R.id.movie_title)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        with(holder) {

            //Update the movie elements and add click listener
            movieView.setOnClickListener(this@MoviesListAdapter)
            movieView.tag = movie
            movieTitle.text = movie.title

            //Image loading via Glide
            Glide.with(context)
                .load(movie.coverUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_movies)
                .into(movieImage)
        }

    }

    override fun getItemCount() = movies.size

    override fun onClick(view: View) {
        when (view.id) {
            R.id.movie_view -> listener?.onMovieSelected(view.tag as Movie)
        }
    }

}