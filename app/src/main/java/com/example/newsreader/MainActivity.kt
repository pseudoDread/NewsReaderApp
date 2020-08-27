package com.example.newsreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.Adapter.ListSourceAdapter
import com.example.newsreader.Model.FavSource
import com.example.newsreader.Model.Source
import com.example.newsreader.Model.WebSite
import com.example.newsreader.Services.ApiClient
import com.example.newsreader.Services.ApiInterface
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() , ListSourceAdapter.OnComponetItemClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ListSourceAdapter
    private var progressBar: ProgressBar? = null

    var sourceslist = ArrayList<Source>()
    var favsourcelist = ArrayList<FavSource>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar

        Paper.init(this)

        recyclerView = findViewById(R.id.recycler_view_source_news)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        loadWebSite()


        fav_btn.setOnClickListener{
            val intent = Intent(this, FavNewsSourceActivity::class.java)
            intent.putParcelableArrayListExtra("arraylist", favsourcelist)
            startActivity(intent)
        }

    }


    private fun loadWebSite() {
        progressBar?.visibility   = View.VISIBLE

            val cache = Paper.book().read<String>("cache")

           if(cache != null && !cache.isBlank())
           {
               //Reading from cache
               val res = Gson().fromJson<WebSite>(cache , WebSite::class.java)
               Log.d("cache to read ", res.toString())
               adapter = ListSourceAdapter(this, res, this)
               recyclerView.adapter = adapter
               recyclerView.adapter?.notifyDataSetChanged()
           }

        else {
               //New data
               val ApiInterface = ApiClient.buildService(ApiInterface::class.java)
               val call = ApiInterface.sources

               call.enqueue(object : Callback<WebSite> {

                   override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                       sourceslist = (response.body()!!.sources as ArrayList<Source>?)!!
                       recyclerView.adapter =
                           ListSourceAdapter(
                               this@MainActivity,
                               response.body()!!,
                               this@MainActivity
                           )
                       recyclerView.adapter?.notifyDataSetChanged()
                       progressBar?.visibility   = View.GONE


                       //saving to cache
                       Paper.book().write("cache", Gson().toJson(response.body()))
                       Log.d("cache to write ", Gson().toJson(response.body()))
                   }

                   override fun onFailure(call: Call<WebSite>, t: Throwable) {

                   }

               })
           }

    }

    override fun onFavBtnClick(position: Int) {

        val clickedFavBtnItem  = sourceslist[position]
        var id = clickedFavBtnItem.id
        var name = clickedFavBtnItem.name
        var description = clickedFavBtnItem.description
        var url = clickedFavBtnItem.url
        var category = clickedFavBtnItem.category
        var language = clickedFavBtnItem.language
        var country = clickedFavBtnItem.country
        var isFav = clickedFavBtnItem.isFav

        if(isFav == 1) {
            var itemtoadd = FavSource(id, name, description, url, category, language, country, isFav)
            favsourcelist.add(itemtoadd)
            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
        }
        if(isFav == 0)
        {
            for(i in favsourcelist)
            {
                if(i.id == id)
                {
                    favsourcelist.remove(i)
                    Toast.makeText(this, "Successfully Removed", Toast.LENGTH_SHORT).show()
                    break
                }

            }
        }

    }


}
