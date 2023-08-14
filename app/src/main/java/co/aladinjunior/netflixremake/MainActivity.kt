package co.aladinjunior.netflixremake

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        progress = findViewById(R.id.main_progress)


        val categories = mutableListOf<Category>()

        CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=454207e4-a780-4ba1-968a-cc22f29d3eae")



        val rv = findViewById<RecyclerView>(R.id.rv_main)
        val adapter = CategoryAdapter(categories)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter


    }

    override fun onSuccess(categories: List<Category>) {
        Log.i("test activity", categories.toString())
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