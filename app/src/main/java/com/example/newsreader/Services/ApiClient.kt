package com.example.newsreader.Services

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

object ApiClient {

    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "7cc6f9c8dfa44fe993467007ada10a9e"


    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getNewsAPI(source : String) : String
    {
        val apiURL = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()

        return apiURL
    }

    fun <T> buildService(serviceType: Class <T>): T
    {
       return retrofit.create(serviceType)
    }



}