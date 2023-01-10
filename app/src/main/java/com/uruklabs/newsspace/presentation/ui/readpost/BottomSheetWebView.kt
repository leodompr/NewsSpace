package com.uruklabs.newsspace.presentation.ui.readpost

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uruklabs.newsspace.databinding.BottomSheetWebViewBinding

class BottomSheetWebView(val url: String) :
    BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetWebViewBinding

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {

        binding = BottomSheetWebViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lvClose.setOnClickListener {
            closeWeb()
        }
        binding.webViewAll.webViewClient = object : WebViewClient() {
            @SuppressLint("SetJavaScriptEnabled")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                view?.settings!!.javaScriptEnabled = true
                return true
            }
        }
        binding.webViewAll.loadUrl(url)

        binding.webViewAll.settings.domStorageEnabled = true
        binding.webViewAll.settings.javaScriptEnabled = true
    }

    fun closeWeb() {
        this.dismiss()
    }
}
