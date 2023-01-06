package com.uruklabs.newsspace.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.google.android.material.appbar.MaterialToolbar
import com.uruklabs.newsspace.R
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory

@BindingAdapter("toolbarTitle")
fun MaterialToolbar.setToolbarTitleFromCategory(category: LiveData<SpaceFlightNewsCategory>?) {

    category?.let {
        val stringRes = when (it.value) {
            SpaceFlightNewsCategory.ARTICLES -> R.string.latest_news
            SpaceFlightNewsCategory.BLOGS -> R.string.latest_blogs
            else -> {
                R.string.latest_reports
            }
        }
        this.title = context.getString(stringRes)
    }

}