package com.example.newsreader.Services

import android.view.View

interface ItemClickListener {
    fun onClick(view: View, position: Int)
}