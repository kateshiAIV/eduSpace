package com.eduspace.fragments

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.eduspace.R
import org.json.JSONObject
import java.net.URL


class picture : Fragment() {

    val API: String = "3rI3DdSaNbh6axkhGABZiWh3xsipwYMty5pfNNdl" // NASA API key 3rI3DdSaNbh6axkhGABZiWh3xsipwYMty5pfNNdl https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY
    private var hdurl: String? = null
    private var url: String? = null
    private lateinit var titleView: EditText
    private lateinit var explanationView: TextView
    private lateinit var link: TextView
    private lateinit var imageView: ImageView
    private lateinit var loader: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        pictureTask().execute()

    }

    inner class pictureTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun doInBackground(vararg p0: String?): String? {
            return try {
                URL("https://api.nasa.gov/planetary/apod?api_key=$API").readText(Charsets.UTF_8)
            } catch (e: Exception) {
                null
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)


            try {
                val jsonObj = JSONObject(result)
                val title = jsonObj.getString("title")
                val explanation = jsonObj.getString("explanation")
                val media_type = jsonObj.getString("media_type")


                link.visibility = View.VISIBLE
                explanationView.visibility = View.VISIBLE
                loader.visibility = View.GONE


                titleView.setText(title)
                explanationView.text = explanation

                if (media_type == "image") {
                    hdurl = jsonObj.getString("hdurl")
                    Glide.with(this@picture)
                        .load(hdurl)
                        .into(imageView)
                    link.setText("Open picture")
                }
                else if (media_type == "video"){
                    url = jsonObj.getString("url")
                    link.setText("Open video")
                    hdurl = url
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_picture, container, false)


        titleView = view.findViewById(R.id.title)
        explanationView = view.findViewById(R.id.explanation)
        imageView = view.findViewById(R.id.image)
        link = view.findViewById(R.id.link)
        loader = view.findViewById(R.id.loader)

        link.setOnClickListener {


            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(hdurl))
            startActivity(browserIntent)

        }

        return view

    }
}