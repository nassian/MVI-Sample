package com.nassiansoft.mvisample.data.network

import com.nassiansoft.mvisample.data.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id:Int):User

    companion object{

        const val BASE_URL="https://jsonplaceholder.typicode.com"

        fun create():UserApi{
            val loggingInterceptor=
                HttpLoggingInterceptor().apply {
                    level=HttpLoggingInterceptor.Level.BASIC
                }
            val client=OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build()

            return Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build().create(UserApi::class.java)
        }

    }
}