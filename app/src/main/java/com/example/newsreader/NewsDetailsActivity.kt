package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivity : AppCompatActivity() {

    var newsURL = ""
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar


        if(intent!=null)
        {
            newsURL = intent.getStringExtra("newsURL").toString()
            if(!newsURL.isEmpty())
            {
                loadNewsDetails(newsURL)
            }
        }


    }

    private fun loadNewsDetails(newsURL : String)
    {


        progressBar?.visibility   = View.VISIBLE
        /*WebView*/
        webView.settings.javaScriptEnabled = true

        webView.loadUrl(newsURL)
        progressBar?.visibility   = View.GONE

    }
}