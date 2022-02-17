package com.bankhapoalimheroes.utils.extensions

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

fun <T, VH : RecyclerView.ViewHolder>
        RecyclerView.setAdapterWithItemDecoration(
    context: Context,
    adapter: ListAdapter<T, VH>
) {
    this.adapter = adapter
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(context)
    this.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
}