package com.bankhapoalimheroes.utils.extensions

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

fun <T, VH : RecyclerView.ViewHolder>
        RecyclerView.setAdapter(
    context: Context,
    adapter: ListAdapter<T, VH>,
    orientationHorizontal: Boolean = false
) {
    this.adapter = adapter
    this.setHasFixedSize(true)
    if (orientationHorizontal.not()) {
        this.layoutManager = LinearLayoutManager(context)
        this.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        return
    }
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}