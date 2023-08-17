package co.aladinjunior.netflixremake

import android.graphics.Bitmap
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import co.aladinjunior.netflixremake.model.Movie
import co.aladinjunior.netflixremake.util.DownloadImageTask
import co.aladinjunior.netflixremake.util.MovieTask

class MovieAdapter(
    private val moviesList: List<Movie>,
    @LayoutRes private val layoutId: Int,
    private val onMovieClickListener: OnMovieClickListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        val currentMovie = moviesList[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie)  {
            val movieImage = itemView.findViewById<ImageView>(R.id.movie_image)
            DownloadImageTask(object : DownloadImageTask.CallBack{
                override fun onSuccess(bitmap: Bitmap) {
                    movieImage.setImageBitmap(bitmap)
                }

            }).execute(movie.coverUrl)


            movieImage.setOnClickListener {
                onMovieClickListener.onClick(movie.id)
            }






        }
    }

}