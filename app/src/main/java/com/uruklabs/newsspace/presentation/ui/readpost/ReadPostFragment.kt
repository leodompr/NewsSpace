package com.uruklabs.newsspace.presentation.ui.readpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.uruklabs.newsspace.databinding.FragmentReadPostBinding
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class ReadPostFragment : Fragment() {

    private val args by navArgs<ReadPostFragmentArgs>()


    private val binding: FragmentReadPostBinding by lazy {
        FragmentReadPostBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.tvPost.text = "    " + args.post.summary
        binding.tvTitle.text = args.post.title
        val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withZone(ZoneId.from(ZoneOffset.UTC))
        with(formatter) {
            binding.itemPublishedCh.text = this.format(Instant.parse(args.post.publishedAt))
        }

        Glide.with(binding.ivPost)
            .load(args.post.imageUrl)
            .into(binding.ivPost)

    }

}