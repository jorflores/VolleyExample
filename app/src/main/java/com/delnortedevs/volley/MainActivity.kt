package com.delnortedevs.volley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.delnortedevs.volley.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener{

            val userName  = binding.editTextGithubUsername.text.toString()

            if (userName != "") {
                getUsers(userName)
            }


        }


    }

    fun getUsers(userName: String) {

        val queue = Volley.newRequestQueue(this)
        val url: String = "https://api.github.com/search/users?q=${userName}"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->


                var strResp = response.toString()
                val jsonObj = JSONObject(strResp)
                var count = jsonObj.get("total_count")
                val jsonArray = jsonObj.getJSONArray("items")
                var str_user = "#Users: $count\n"
                for (i in 0 until jsonArray.length()) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                    str_user = str_user + "\n" + jsonInner.get("login")

                }
                binding.TextViewGithubUsers.text = str_user

            },
            { binding.TextViewGithubUsers.text = "That didn't work!" })
        queue.add(stringReq)
    }

}