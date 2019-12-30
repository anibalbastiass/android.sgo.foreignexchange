package com.anibalbastias.android.foreignexchange.data.foreignexchange

import android.annotation.SuppressLint
import android.os.SystemClock
import android.util.Log
import com.anibalbastias.android.foreignexchange.presentation.context
import okhttp3.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI

/**
 * Created by Anibal Bastias Soto on 2019-12-30.
 */

@SuppressLint("DefaultLocale")
class FakeInterceptor : Interceptor {

    companion object {
        private val TAG = FakeInterceptor::class.java.simpleName
        private const val contentType = "application/json;charset=UTF-8"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        SystemClock.sleep(1000)

        var response: Response? = null
        val uri = chain.request().url().uri()
        val randomCode = 200
        val defaultFileName = getFileName(chain)
        val suggestionFileName = defaultFileName?.toLowerCase()!!
        var fileName: String = getFilePath(uri, suggestionFileName)

        fileName += ".json"

        Log.d(TAG, "Read data from file: $fileName")

        try {
            val `is`: InputStream = context?.assets?.open(fileName)!!
            val r = BufferedReader(InputStreamReader(`is`))
            val responseStringBuilder = StringBuilder()
            var line: String?

            while (r.readLine().also { line = it } != null) {
                responseStringBuilder.append(line).append('\n')
            }

            val builder = Response.Builder().code(randomCode)
                .message(responseStringBuilder.toString())
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(
                    ResponseBody.create(
                        MediaType.parse(contentType),
                        responseStringBuilder.toString().toByteArray()
                    )
                )
                .addHeader("content-type", contentType)

            response = builder.build()
        } catch (e: IOException) {
            Log.e(TAG, e.message, e)
        }

        return response!!
    }

    private fun getFilePath(uri: URI, fileName: String): String {
        val path: String = if (uri.path.lastIndexOf('/') != uri.path.length - 1) {
            uri.path.substring(0, uri.path.lastIndexOf('/') + 1)
        } else {
            uri.path
        }
        return uri.host + path.toLowerCase() + fileName
    }

    private fun getFileName(chain: Interceptor.Chain): String? {
        val fileName =
            chain.request().url().pathSegments()[chain.request().url().pathSegments().size - 1]
        return if (fileName.isEmpty()) "index" else fileName + "_" + chain.request().method().toLowerCase()
    }
}