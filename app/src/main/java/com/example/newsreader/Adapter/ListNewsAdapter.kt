package com.example.newsreader.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.ListNewsActivity
import com.example.newsreader.Model.Article
import com.example.newsreader.NewsDetailsActivity
import com.example.newsreader.R
import com.example.newsreader.Services.ItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_layout.view.*

class ListNewsAdapter(val context: Context, val articleList : MutableList<Article> ) :
RecyclerView.Adapter<ListNewsAdapter.ListNewsViewHolder>()
{
    inner class ListNewsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) ,
            View.OnClickListener
    {
        var article_title : TextView = itemView.article_title
        var article_image = itemView.article_image

        private lateinit var itemClickListener: ItemClickListener

        init {
            itemView.setOnClickListener(this)
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(v: View?) {
            itemClickListener.onClick(v!!, adapterPosition)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_layout, parent, false)

        return ListNewsViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        var currentItem = articleList[position]

        if(currentItem.title!!.length > 70)
        {
            holder.article_title.text = currentItem.title!!.substring(0, 70) +"..."
        }
        else
        {
            holder.article_title.text = currentItem.title!!
        }

        /*Image Load*/
        Picasso.get().load(articleList[position].urlToImage).into(holder.article_image)



        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, NewsDetailsActivity::class.java)
                intent.putExtra("newsURL", articleList[position]!!.url)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        })

    }


    override fun getItemCount(): Int {
        return articleList.size
    }

}