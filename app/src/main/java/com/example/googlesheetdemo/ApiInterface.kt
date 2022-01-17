package com.example.googlesheetdemo

import com.github.theapache64.retrosheet.annotations.Write
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by theapache64 : Aug 29 Sat,2020 @ 10:14
 */
interface ApiInterface {

    // @Read("SELECT * ORDER BY created_at DESC")
    @GET("demo")
    fun getNotes(): Call<List<Read>> // you can also use suspend and return List<Note>

    @Write
    @POST("add_demo")
    fun add(@Body note: AddData): Call<AddData>

}