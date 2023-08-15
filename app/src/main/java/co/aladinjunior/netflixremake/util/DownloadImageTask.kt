package co.aladinjunior.netflixremake.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class DownloadImageTask(private val callback: CallBack) {

    val executor = Executors.newSingleThreadExecutor()
    val handler = Handler(Looper.getMainLooper())

    interface CallBack{
        fun onSuccess(bitmap: Bitmap)
    }

    fun execute(url: String){



        executor.execute {
            var connection: HttpsURLConnection? = null
            var stream: InputStream? = null
            try {
                val requestURL = URL(url)
                connection = requestURL.openConnection() as HttpsURLConnection
                if(connection.responseCode > 400) {
                    throw IOException("Erro na comunicação com o servidor")
                }
                stream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(stream)

                handler.post {
                    callback.onSuccess(bitmap)
                }


            } catch (e: IOException) {
                val message = e.message ?: "erro desconhecido"
                Log.e("exceptions", message, e)

            } finally {
                connection?.disconnect()
                stream?.close()
            }


        }
    }






}