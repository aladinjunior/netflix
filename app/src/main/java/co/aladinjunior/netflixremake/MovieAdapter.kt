package co.aladinjunior.netflixremake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import co.aladinjunior.netflixremake.model.Movie

class MovieAdapter(private val moviesList: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        val currentMovie = moviesList[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

     inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
         fun bind(movie: Movie){
             val movieImage = itemView.findViewById<ImageView>(R.id.movie_image)
             movieImage.setImageResource(movie.imageCover)
         }
    }

}