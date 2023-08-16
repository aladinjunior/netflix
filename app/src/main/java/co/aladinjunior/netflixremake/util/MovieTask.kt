package co.aladinjunior.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import co.aladinjunior.netflixremake.model.Movie
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection


class MovieTask {

    fun execute(url: String) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            var connect: HttpsURLConnection? = null
            var stream: InputStream? = null
            try {
                val requestURL = URL(url)
                connect = requestURL.openConnection() as HttpsURLConnection
                if(connect.responseCode > 400) throw IOException("Erro na comunicação com o servidor!")
                stream = connect.inputStream
                val stringJson = stream.bufferedReader().use { it.readText() }
                val x = parseMovie(stringJson)
                Log.i("test", "$x")


            } catch (e: IOException) {

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
}