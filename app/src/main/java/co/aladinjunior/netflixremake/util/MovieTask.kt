package co.aladinjunior.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import co.aladinjunior.netflixremake.model.Movie
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.lang.IllegalStateException
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection


class MovieTask(private val callback: CallBack) {

    interface CallBack{
        fun onSuccess(movie: Movie)
        fun onFailure(message: String)

    }

    fun execute(url: String) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            var connect: HttpsURLConnection? = null
            var stream: InputStream? = null
            try {
                val requestURL = URL(url)
                connect = requestURL.openConnection() as HttpsURLConnection
                if(connect.responseCode == 400) {
                    stream = connect.errorStream
                    val stringJson = stream.bufferedReader().use { it.readText() }
                    val message = parseString(stringJson)
                    throw IllegalStateException(message)
                } else if(connect.responseCode > 400) throw IOException("Erro na comunicação com o servidor!")
                stream = connect.inputStream
                val stringJson = stream.bufferedReader().use { it.readText() }
                val movie = parseMovie(stringJson)
                handler.post {
                    callback.onSuccess(movie)
                }


            } catch (e: IllegalStateException) {
                handler.post {
                    callback.onFailure(e.message!!)
                }

            } finally {
                connect?.disconnect()
                stream?.close()
            }
        }
    }

    private fun parseMovie(stringJson: String) : Movie {
        val root = JSONObject(stringJson)
        val id = root.getInt("id")
        val title = root.getString("title")
        val description = root.getString("desc")
        val cast = root.getString("cast")
        val coverUrl = root.getString("cover_url")
        val similar = root.getJSONArray("movie")

        val movies = mutableListOf<Movie>()
        for (i in 0 until similar.length()){
            val currentMovie = similar.getJSONObject(i)
            val id = currentMovie.getInt("id")
            val coverUrl = currentMovie.getString("cover_url")
            movies.add(Movie(id, coverUrl))
        }

        return Movie(id, coverUrl, title, description, cast, movies)
    }

    private fun parseString(stringJson: String) : String {
        val root = JSONObject(stringJson)
        val message = root.getString("message")
        return message
    }
}