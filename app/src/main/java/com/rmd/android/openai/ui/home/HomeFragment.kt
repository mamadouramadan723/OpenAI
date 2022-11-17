package com.rmd.android.openai.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.rmd.android.openai.R
import com.rmd.android.openai.databinding.FragmentHomeBinding
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private var myTweet: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializations
        binding = FragmentHomeBinding.bind(view)
        binding.enterBtn.setOnClickListener {
            myTweet = binding.inputTweetEdt.text.toString()
            getResponse()
        }
    }

    private fun getResponse() {

        val apiUrl = "https://api.openai.com/v1/completions"
        // creating a variable for request queue.
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            apiUrl,
            null,
            Response.Listener { response ->

                try {
                    // on below line we are getting data from our response
                    // and setting it in variables
                    // extracting data from json.

                    //val name = response.getString("choices")
                    val dataArray = response.getJSONArray("choices")

                    val dataObj = dataArray.getJSONObject(0)
                    val name = dataObj.getString("text")

                    binding.textHome.text = name


                    //ipInfos.ip = response.getString("ip")
                } catch (e: JSONException) {
                    // handling json exception.
                    e.printStackTrace()
                    Toast.makeText(
                        context,
                        "Something went amiss. Please try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener { error ->
                // displaying error response when received any error.
                Log.e("++++ : ", error.toString())

            }) {
            override fun getHeaders(): Map<String, String> {
                // in this method passing headers as
                // key along with value as API keys.
                val headers: HashMap<String, String> = HashMap()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "Bearer " + resources.getString(R.string.OpenAI_API_KEY)

                // at last returning headers
                return headers
            }


            override fun getBody(): ByteArray {
                val body = HashMap<Any?, Any?>()

                body["model"] = "text-davinci-002"
                body["prompt"] =  "Decide whether a Tweet's sentiment is positive, neutral, or negative.\n\nTweet: \"${myTweet}\"\nSentiment:"

                return JSONObject(body).toString().toByteArray()
            }
        }

        // calling a method to add our
        // json object request to our queue.
        queue.add(jsonObjectRequest)
    }


}