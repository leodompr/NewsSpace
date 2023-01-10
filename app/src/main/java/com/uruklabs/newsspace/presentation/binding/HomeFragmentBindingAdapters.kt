package com.uruklabs.newsspace.presentation.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.uruklabs.newsspace.R
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory

@BindingAdapter("textViewText")
fun TextView.titleFromCategory(category: LiveData<SpaceFlightNewsCategory>?) {

    category?.let {
        val stringRes = when (it.value) {
            SpaceFlightNewsCategory.ARTICLES -> R.string.latest_news
            SpaceFlightNewsCategory.BLOGS -> R.string.latest_blogs
            else -> {
                R.string.latest_reports
            }
        }
        this.text = context.getString(stringRes)
    }
}
