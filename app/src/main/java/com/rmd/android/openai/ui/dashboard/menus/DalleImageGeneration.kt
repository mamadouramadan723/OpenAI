package com.rmd.android.openai.ui.dashboard.menus

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.rmd.android.openai.R
import com.rmd.android.openai.databinding.FragmentMenuDalleImagegenerationBinding
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject


class DalleImageGeneration : Fragment() {

    private lateinit var binding: FragmentMenuDalleImagegenerationBinding

    private lateinit var progressDialog: ProgressDialog
    private var myDescription: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu_dalle_imagegeneration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializations
        binding = FragmentMenuDalleImagegenerationBinding.bind(view)
        progressDialog = ProgressDialog(requireContext())
        binding.enterBtn.setOnClickListener {
            myDescription = binding.inputTweetEdt.text.toString()
            getResponse()
        }
    }

    private fun getResponse() {

        progressDialog.setMessage("Loading...")
        progressDialog.show()

        val apiUrl = "https://api.openai.com/v1/images/generations"
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
                    val dataArray = response.getJSONArray("data")

                    val dataObj = dataArray.getJSONObject(0)
                    val url = dataObj.getString("url")

                    Picasso.get()
                        .load(url).into(binding.generatedImv)

                    progressDialog.dismiss()

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
                if (error.networkResponse == null && error.javaClass.simpleName.toString() == "TimeoutError") {
                    Toast.makeText(
                        context,
                        "oops TimeOut",
                        Toast.LENGTH_SHORT
                    ).show()
                }


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

                body["prompt"] = myDescription
                body["size"] = 1
                body["size"] = "1024x1024"

                return JSONObject(body).toString().toByteArray()
            }
        }
        jsonObjectRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 30000
            }

            override fun getCurrentRetryCount(): Int {
                return 30000
            }

            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
            }
        }

        // calling a method to add our
        // json object request to our queue.
        queue.add(jsonObjectRequest)
    }
}