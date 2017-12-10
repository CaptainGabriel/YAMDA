package com.dev.moviedb.mvvm.nowPlayingMoviesTab

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.dialog_video_player_layout.*
import petegabriel.com.yamda.R


/**
 *
 * Yamda 1.0.0.
 */
class VideoPlayerFragment : DialogFragment() {

    private var videoId: String? = ""

    companion object {

        val VIDEO_ID_ARGS_KEY : String = "youtube_player_video_id_argument_key"

        fun newInstance(id: String): VideoPlayerFragment {
            val frag = VideoPlayerFragment()
            val args = Bundle()
            args.putString(VIDEO_ID_ARGS_KEY, id)
            frag.arguments = args
            return frag
        }
    }


    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.dialog_video_player_layout, container, false)

        videoId = arguments.getString(VIDEO_ID_ARGS_KEY)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        youtube_player_view.initialize({ initializedYouTubePlayer ->
            initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                override fun onReady() {
                    initializedYouTubePlayer.loadVideo(videoId, 0f)
                }
            })
        }, true)
    }

    override fun onStop() {
        super.onStop()
        youtube_player_view.release()
    }
}