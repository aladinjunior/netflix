package co.aladinjunior.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = Adapter()
        val rv = findViewById<RecyclerView>(R.id.rv_main)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    private inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
            val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        }

        override fun getItemCount(): Int {
            return 60
        }

        private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        }

    }
}