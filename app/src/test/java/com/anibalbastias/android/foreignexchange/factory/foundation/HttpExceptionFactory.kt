package com.anibalbastias.android.foreignexchange.factory.foundation

import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

object HttpExceptionFactory {

    fun <T> makeHttpException(response: Response<T>): HttpException {
        return HttpException(response)
    }

    fun <T> makeErrorResponse(content: String): Response<T> {
        return Response.error<T>(500, ResponseBody.create(null, content))
    }

}