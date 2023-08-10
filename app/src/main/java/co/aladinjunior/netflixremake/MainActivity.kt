package co.aladinjunior.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.aladinjunior.netflixremake.model.Category
import co.aladinjunior.netflixremake.model.Movie

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val movies = mutableListOf<Movie>()
        val categories = mutableListOf<Category>()


        for (x in 0 until 10) {

            for (i in 0 until 5){
                movies.add(Movie(R.drawable.movie))
            }
            categories.add(Category("categoria $x", movies))
        }

        val rv = findViewById<RecyclerView>(R.id.rv_main)
        val adapter = CategoryAdapter(categories)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter



    }


}