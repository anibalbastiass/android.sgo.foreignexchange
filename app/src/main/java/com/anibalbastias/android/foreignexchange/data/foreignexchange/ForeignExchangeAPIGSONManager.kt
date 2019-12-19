package com.anibalbastias.android.foreignexchange.data.foreignexchange

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class ForeignExchangeAPIGSONManager private constructor() {

    private fun createGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    private fun createGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()

//        gsonBuilder.registerTypeAdapter(TypeData::class.java, MessageDeserializer())

        return gsonBuilder
    }

    companion object {

        private var instance: ForeignExchangeAPIGSONManager? = null

        fun create(): Gson {
            if (instance == null) {
                instance = ForeignExchangeAPIGSONManager()
            }
            return instance!!.createGson()
        }

        fun createGsonBuilder(): GsonBuilder {
            if (instance == null) {
                instance = ForeignExchangeAPIGSONManager()
            }
            return instance!!.createGsonBuilder()
        }
    }
}