package com.example.newsreader.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.FavNewsSourceActivity
import com.example.newsreader.ListNewsActivity
import com.example.newsreader.Model.FavSource
import com.example.newsreader.Model.Source
import com.example.newsreader.Model.WebSite
import com.example.newsreader.R
import com.example.newsreader.Services.ItemClickListener
import kotlinx.android.synthetic.main.source_news_layout.view.*

class ListFavSourceAdapter(private val context: Context, private val favsourcelist: ArrayList<FavSource>, private val componentItemClicklistener: OnComponetItemClickListener) :
    RecyclerView.Adapter<ListFavSourceAdapter.ViewHolder>()
{

    //inserted
    interface OnComponetItemClickListener{
        fun onFavBtnClick(position : Int)
    }
    //end


    inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) ,
    View.OnClickListener
    {
        var source_title: TextView = itemView.source_news_name
        var favBtn: ImageView = itemView.findViewById(R.id.source_fav_btn)

        private lateinit var itemClickListener: ItemClickListener

        init {
            itemView.setOnClickListener(this)

            //inserted
            favBtn.setOnClickListener{
                componentItemClicklistener.onFavBtnClick(adapterPosition)
            }
            //end
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(p0: View?) {
            itemClickListener.onClick(p0!!, adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_newssource_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return favsourcelist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var currentitem = favsourcelist[position]

        holder.source_title.text= currentitem.name
        holder.favBtn.setImageResource(R.drawable.ic_fav)

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, ListNewsActivity::class.java)
                intent.putExtra("source", favsourcelist!![position].id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        })

    }



    }