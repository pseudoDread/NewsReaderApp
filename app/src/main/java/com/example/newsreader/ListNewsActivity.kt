package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.Adapter.ListNewsAdapter
import com.example.newsreader.Adapter.ListSourceAdapter
import com.example.newsreader.Model.News
import com.example.newsreader.Model.WebSite
import com.example.newsreader.Services.ApiClient
import com.example.newsreader.Services.ApiInterface
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ListNewsActivity : AppCompatActivity() {

    var source = ""

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ListNewsAdapter
    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar

        recyclerView = findViewById(R.id.list_news)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        if(intent != null)
        {
          source = intent.getStringExtra("source").toString()
           if(!source.isEmpty())
           {
               loadNewsList(source)
           }
        }

    }

    private fun loadNewsList(sources : String?) {

        progressBar?.visibility   = View.VISIBLE
        val ApiInterface = ApiClient.buildService(ApiInterface::class.java)
        val call = ApiInterface.getNewsFromSource(ApiClient.getNewsAPI(sources!!))

        call.enqueue(object : retrofit2.Callback<News> {

            override fun onResponse(call: Call<News>, response: Response<News>) {

                recyclerView.adapter = ListNewsAdapter(this@ListNewsActivity, response.body()!!.articles!! )
                recyclerView.adapter?.notifyDataSetChanged()
                progressBar?.visibility   = View.GONE

            }

            override fun onFailure(call: Call<News>, t: Throwable) {

            }

        })


    }
}