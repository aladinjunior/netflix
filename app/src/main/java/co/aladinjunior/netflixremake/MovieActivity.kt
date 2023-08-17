package co.aladinjunior.netflixremake

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.aladinjunior.netflixremake.model.Movie
import co.aladinjunior.netflixremake.util.DownloadImageTask
import co.aladinjunior.netflixremake.util.MovieTask
import java.io.IOException
import java.lang.NullPointerException

class MovieActivity : AppCompatActivity(), MovieTask.CallBack {


    private val movies = mutableListOf<Movie>()
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: MovieAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var title: TextView
    private lateinit var sinopse: TextView
    private lateinit var cast: TextView
    private lateinit var similar: TextView
    private lateinit var icPlay: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)


        progressBar = findViewById(R.id.movie_progress)
        toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = null

        val id =
            intent.extras?.getInt("id") ?: throw IOException("filme com id inválido!")

        val url =
            "https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=454207e4-a780-4ba1-968a-cc22f29d3eae"





        MovieTask(this).execute(url)


        adapter =
            MovieAdapter(movies, R.layout.movie_similar_item, object : OnMovieClickListener {
                override fun onClick(id: Int) {
                }
            })
        val rv = findViewById<RecyclerView>(R.id.rv_similar)
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onSuccess(movie: Movie) {
        setImageBackground(movie)
        setMovieDetails(movie)
        movies.clear()
        movies.addAll(movie.similars!!)
        adapter.notifyDataSetChanged()
        progressBar.visibility = View.GONE


    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        progressBar.visibility = View.GONE
    }

    private fun setImageBackground(movie: Movie) {

        DownloadImageTask(object : DownloadImageTask.CallBack {
            override fun onSuccess(bitmap: Bitmap) {
                val bitmapDrawable = BitmapDrawable(this@MovieActivity.resources, bitmap)
                val layerDrawable: LayerDrawable = ContextCompat.getDrawable(
                    this@MovieActivity,
                    R.drawable.movie_background_shadows
                ) as LayerDrawable
                layerDrawable.setDrawableByLayerId(R.id.covered_movie_background, bitmapDrawable)
                val movieBackground: ImageView = findViewById(R.id.movie_background)
                movieBackground.setImageDrawable(layerDrawable)

            }

        }).execute(movie.coverUrl)

    }

    private fun setMovieDetails(movie: Movie) {
        title = findViewById(R.id.movie_name)
        val rTitle = resources.getString(R.string.movie_name, movie.title)
        title.setText(rTitle)
        sinopse = findViewById(R.id.movie_sinopse)
        val rSinopse = resources.getString(R.string.movie_sinopse, movie.description)
        sinopse.setText(rSinopse)
        cast = findViewById(R.id.movie_cast)
        val rCast = resources.getString(R.string.movie_cast, movie.cast)
        cast.setText(rCast)
        similar = findViewById(R.id.movie_similar)
        val rSimilar = resources.getString(R.string.similar_options)
        similar.setText(rSimilar)
        icPlay = findViewById(R.id.ic_play)
        icPlay.visibility = View.VISIBLE

    }
}