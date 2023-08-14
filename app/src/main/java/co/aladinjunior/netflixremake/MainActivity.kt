package co.aladinjunior.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.aladinjunior.netflixremake.model.Category
import co.aladinjunior.netflixremake.model.Movie
import co.aladinjunior.netflixremake.util.CategoryTask

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val categories = mutableListOf<Category>()

        CategoryTask().execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=454207e4-a780-4ba1-968a-cc22f29d3eae")



        val rv = findViewById<RecyclerView>(R.id.rv_main)
        val adapter = CategoryAdapter(categories)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter



    }


}