package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.Adapter.ListFavSourceAdapter
import com.example.newsreader.Model.FavSource
import kotlinx.android.synthetic.main.activity_fav_news_source.*

class FavNewsSourceActivity : AppCompatActivity() , ListFavSourceAdapter.OnComponetItemClickListener {

    var exlist = ArrayList<FavSource>()
    lateinit var recyclerView: RecyclerView
    private var progressBar: ProgressBar? = null

    var adapter = ListFavSourceAdapter(this, exlist, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_news_source)


        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar


        var data = intent.extras
        val cominglist = data?.getParcelableArrayList<FavSource>("arraylist")

        exlist = cominglist as ArrayList<FavSource>



        if(exlist.isNotEmpty()) {
            progressBar?.visibility   = View.VISIBLE

            Saved_Info_Text.visibility = View.INVISIBLE


            recyclerView = findViewById(R.id.recycler_view)

            progressBar?.visibility   = View.GONE

            recyclerView.adapter = ListFavSourceAdapter(this, exlist, this)
            recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerView.setHasFixedSize(true)

        }
        else
        {
            Saved_Info_Text.visibility = View.VISIBLE
        }


    }

    override fun onFavBtnClick(position: Int) {
        val clickedFavBtnItem  = exlist[position]
        var id = clickedFavBtnItem.id
        var name = clickedFavBtnItem.name
        var description = clickedFavBtnItem.description
        var url = clickedFavBtnItem.url
        var category = clickedFavBtnItem.category
        var language = clickedFavBtnItem.language
        var country = clickedFavBtnItem.country
        var isFav = clickedFavBtnItem.isFav

        for(i in exlist)
        {
            if(i.id == id)
            {
                exlist.remove(i)
                Toast.makeText(this, "Successfully Removed", Toast.LENGTH_SHORT).show()
                recyclerView.adapter?.notifyDataSetChanged()
                break
            }

        }

        if(exlist.isEmpty())
        {
            Saved_Info_Text.visibility = View.VISIBLE
        }
    }


}