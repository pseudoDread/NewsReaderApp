package com.example.newsreader.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.ListNewsActivity
import com.example.newsreader.Model.WebSite
import com.example.newsreader.R
import com.example.newsreader.Services.ItemClickListener
import kotlinx.android.synthetic.main.source_news_layout.view.*

class ListSourceAdapter(private val context: Context, val webSite : WebSite, private val componentItemClicklistener : OnComponetItemClickListener ):
RecyclerView.Adapter<ListSourceAdapter.ListSourceViewHolder>()
{

    interface OnComponetItemClickListener{
        fun onFavBtnClick(position : Int)
    }


    inner class ListSourceViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) ,
            View.OnClickListener {
       var source_title : TextView = itemView.source_news_name
        var favBtn: ImageView = itemView.findViewById(R.id.source_fav_btn)


        private lateinit var itemClickListener: ItemClickListener

        init {
            itemView.setOnClickListener(this)
            favBtn.setOnClickListener{

                var checkfav = webSite.sources!![adapterPosition].isFav

                if(checkfav == 0)
                {
                    webSite.sources!![adapterPosition].isFav = 1
                    favBtn.setImageResource(R.drawable.ic_fav)
                }
                else
                {
                    webSite.sources!![adapterPosition].isFav = 0
                    favBtn.setImageResource(R.drawable.ic_unfav)
                }

//                webSite.sources!![adapterPosition].isFav == 1
//                favBtn.setImageResource(R.drawable.ic_fav)
                componentItemClicklistener.onFavBtnClick(adapterPosition)
            }

        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(v: View?) {
            itemClickListener.onClick(v!!, adapterPosition)
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.source_news_layout, parent, false)

        return ListSourceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {

      var currentItem = webSite.sources!![position]

        holder.source_title.text= currentItem.name

        if(webSite.sources!![position].isFav == 1 )
        {
            holder.favBtn.setImageResource(R.drawable.ic_fav)
        }
        else
        {
            holder.favBtn.setImageResource(R.drawable.ic_unfav)
        }


        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, ListNewsActivity::class.java)
                intent.putExtra("source", webSite.sources!![position].id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        })

    }

    override fun getItemCount(): Int {
       return webSite.sources!!.size
    }




}