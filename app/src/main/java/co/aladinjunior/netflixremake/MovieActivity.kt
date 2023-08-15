package co.aladinjunior.netflixremake

import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.aladinjunior.netflixremake.model.Movie

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val toolbar = findViewById<Toolbar>(R.id.movie_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = null

        val layerDrawable: LayerDrawable = ContextCompat.getDrawable(this, R.drawable.movie_background_shadows) as LayerDrawable
        val bgDrawable = ContextCompat.getDrawable(this, R.drawable.movie_4)
        layerDrawable.setDrawableByLayerId(R.id.covered_movie_background, bgDrawable)
        val movieBackground: ImageView = findViewById(R.id.movie_background)
        movieBackground.setImageDrawable(layerDrawable)

        val movies = mutableListOf<Movie>()


        val adapter = MovieAdapter(movies, R.layout.movie_similar_item, object : OnMovieClickListener{
            override fun onClick(id: Int) {
            }
        })
        val rv = findViewById<RecyclerView>(R.id.rv_similar)
        rv.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        rv.adapter = adapter

    }
}