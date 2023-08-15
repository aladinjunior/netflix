package co.aladinjunior.netflixremake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.aladinjunior.netflixremake.model.Category
import co.aladinjunior.netflixremake.model.Movie
import co.aladinjunior.netflixremake.util.CategoryTask

class MainActivity : AppCompatActivity(), CategoryTask.CallBack {
    private lateinit var progress: ProgressBar
    private lateinit var adapter: CategoryAdapter
    private val categories = mutableListOf<Category>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        progress = findViewById(R.id.main_progress)







        CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=454207e4-a780-4ba1-968a-cc22f29d3eae")



        val rv = findViewById<RecyclerView>(R.id.rv_main)
        adapter = CategoryAdapter(categories, object : OnMovieClickListener {
            override fun onClick(id: Int) {
                val i = Intent(this@MainActivity, MovieActivity::class.java)
                startActivity(i)
                Log.i("test", "clicou no id $id")
            }

        })
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter


    }

    override fun onSuccess(categories: List<Category>)  {
        this.categories.clear()
        this.categories.addAll(categories)
        adapter.notifyDataSetChanged()
        progress.visibility = View.GONE


    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        progress.visibility = View.GONE
    }

    override fun onStartCall() {
        progress.visibility = View.VISIBLE
    }


}