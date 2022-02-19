package com.bankhapoalimheroes.utils.custom_implementations

abstract class OnSearchViewOnlyTextChangedListener : androidx.appcompat.widget.SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(p0: String?): Boolean {

        return false
    }
}