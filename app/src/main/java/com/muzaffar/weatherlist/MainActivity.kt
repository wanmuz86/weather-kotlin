package com.muzaffar.weatherlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView;
    lateinit var adapter: CustomAdapter;
    lateinit var cityEditText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         recyclerView = findViewById(R.id.recyclerView)
        cityEditText = findViewById(R.id.cityEditText)

        // Get the data


        adapter = CustomAdapter()
        recyclerView.adapter = adapter
    }

    fun searchPressed(view: View) {

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/forecast/daily?q=${cityEditText.text}&apiKey=9fd7a449d055dba26a982a3220f32aa2"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                var responseJSON = JSONObject(response)
                var weatherArray = responseJSON.getJSONArray("list")
                var weatherList = LinkedList<JSONObject>()
                for (i in 0..weatherArray.length()-1){
                    weatherList.add(weatherArray.getJSONObject(i))
                }
                adapter.weatherList = weatherList

                recyclerView.adapter = adapter


            },
            Response.ErrorListener { print("That didn't work!") })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}