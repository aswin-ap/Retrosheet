package com.example.googlesheetdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.theapache64.retrosheet.RetrosheetInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/*
* Refered links : https://github.com/theapache64/retrosheet
*               https://github.com/theapache64/notes
* Form link: https://docs.google.com/forms/d/15iZQ0eGLhu8tXdJ7zvkN1RIY8blttzFXl8W3gG_jsqc/edit
* sheet link: https://docs.google.com/spreadsheets/d/1m_DW-hrQQXObOBso6_Pxs343kPCqKn0YaDZCS8aZa9U/edit?resourcekey#gid=1738164462
* */
class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        // readData()
        button.setOnClickListener {
            readData()
        }
    }

    private fun readData() {
        // Building Retrosheet Interceptor
        val retrosheetInterceptor = RetrosheetInterceptor.Builder()
            .setLogging(false)
            // To Read
            .addSheet(
                "demo", // sheet name
                "created_at", "Title", "Description" // columns in same order
            )
            // To write
            .addForm(
                "add_demo",
                "https://docs.google.com/forms/d/e/1FAIpQLSc92K9oNg1oV4HWPgRdwLO7m5xGDzRA_ydURoKBJZDHgc11AQ/viewform?usp=sf_link" // form link
            )
            .build()

        // Building OkHttpClient
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(retrosheetInterceptor) // and attaching interceptor
            .build()


        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory()).build()

        // Building retrofit client
        val retrofit = Retrofit.Builder()
            // with baseUrl as sheet's public URL
            .baseUrl("https://docs.google.com/spreadsheets/d/1m_DW-hrQQXObOBso6_Pxs343kPCqKn0YaDZCS8aZa9U/") // Sheet's public URL
            // and attach previously created OkHttpClient
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        // Now create the API interface
        val notesApi = retrofit.create(ApiInterface::class.java)
        /*val call = notesApi.getNotes()
        call.enqueue(object : Callback<List<Read>> {
            override fun onResponse(call: Call<List<Read>>, response: Response<List<Read>>) {
                Log.d("API response", response.body().toString())
            }

            override fun onFailure(call: Call<List<Read>>, t: Throwable) {
                Log.d("API Fail", t.message.toString())
            }

        })*/
        val write = notesApi.add(AddData("Hello", "worldddddd"))
        write.enqueue(object : Callback<AddData> {
            override fun onResponse(call: Call<AddData>, response: Response<AddData>) {
                Toast.makeText(this@MainActivity, "Susccesss", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<AddData>, t: Throwable) {
                Log.d("API Fail", t.message.toString())
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }
}