package com.example.dualidad.fragments

import android.os.Bundle
import android.util.Log
import android.view.InflateException
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.dualidad.MainActivity
import com.example.dualidad.R
import com.example.dualidad.databinding.FragmentDetailsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.io.IOException
import java.lang.Exception


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var youTubePlayerView: YouTubePlayerView
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        setVideo()
    }

    private fun setVideo() {
        try {
            binding.apply {
                title.text = args.video.title
                desc.text = args.video.desc
            }
            youTubePlayerView = binding.youtubePlayerView
            lifecycle.addObserver(youTubePlayerView)
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(args.video.url, 0F)
                }
            })
        } catch (e: IOException) {
            Log.e("error", "${e.stackTrace}")
        } catch (e: Exception) {
            Log.e("error", "${e.stackTrace}")
        } catch (e: InflateException) {
            Log.e("error", "${e.stackTrace}")
        }
    }
}