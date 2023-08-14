package co.aladinjunior.netflixremake.util

import android.util.Log
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask {

    fun execute(url: String) {

        val executor = Executors.newSingleThreadExecutor()

        executor.execute {

            try {
                val requestURL = URL(url)
                val connection = requestURL.openConnection() as HttpsURLConnection

                if (connection.responseCode > 400) {
                    throw IOException("Erro de comunicação com o servidor!")
                }

                val stream = connection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() }
                Log.i("json", jsonAsString)

            } catch (e: IOException) {
                Log.e("test", e.message ?: "erro desconhecido", e)
            }


        }


    }

}