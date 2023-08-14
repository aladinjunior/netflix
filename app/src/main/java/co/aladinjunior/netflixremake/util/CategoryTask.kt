package co.aladinjunior.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import co.aladinjunior.netflixremake.model.Category
import co.aladinjunior.netflixremake.model.Movie
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask(private val callback: CallBack) {

    interface CallBack{
        fun onStartCall()
        fun onSuccess(categories: List<Category>)
        fun onFailure(message: String)


    }

    fun execute(url: String) {

        val handler = Handler(Looper.getMainLooper())
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {

            var connection: HttpsURLConnection? = null
            var stream: InputStream? = null


            try {
                val requestURL = URL(url)
                connection = requestURL.openConnection() as HttpsURLConnection

                if (connection.responseCode > 400) {
                    throw IOException("Erro de comunicação com o servidor!")
                }

                stream = connection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() }
                val categories = toCategories(jsonAsString)

                handler.post {
                    callback.onSuccess(categories)
                }





            } catch (e: IOException) {
                callback.onFailure(e.message ?: "erro desconhecido")
            } finally {
                connection?.disconnect()
                stream?.close()
            }







        }


    }


    fun toCategories(jsonAsString: String) : List<Category> {
        val categories = mutableListOf<Category>()

        val jsonRoot = JSONObject(jsonAsString)
        val jsonCategories = jsonRoot.getJSONArray("category")

        for (i in 0 until jsonCategories.length()){
            val jsonCategory = jsonCategories.getJSONObject(i)
            val title = jsonCategory.getString("title")
            val movie = jsonCategory.getJSONArray("movie")

            val movies = mutableListOf<Movie>()
            for (x in 0 until movie.length()){
                val jsonMovie = movie.getJSONObject(x)
                val id = jsonMovie.getInt("id")
                val coverUrl = jsonMovie.getString("cover_url")
                movies.add(Movie(id, coverUrl))
            }

            categories.add(Category(title, movies))
        }

        return categories
    }


}