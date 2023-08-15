package co.aladinjunior.netflixremake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.aladinjunior.netflixremake.model.Category

class CategoryAdapter(
    val categories: List<Category>,
    val onMovieClickListener: OnMovieClickListener
    ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCategory = categories[position]
        holder.bind(currentCategory)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

     inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(category: Category){
            val categoryName = itemView.findViewById<TextView>(R.id.tv_category)
            categoryName.text = category.title

            val categoryRV = itemView.findViewById<RecyclerView>(R.id.rv_category)
            categoryRV.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            categoryRV.adapter = MovieAdapter(category.movies, R.layout.movie_item, onMovieClickListener)
        }
    }


}