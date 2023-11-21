package com.hadis.mymusicapplication11

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cleveroad.play_widget.PlayLayout
import com.hadis.mymusicapplication.currentMusic
import com.hadis.mymusicapplication.databinding.FragmentCurrentPlayingBinding
import com.hadis.mymusicapplication.playMusic
import com.hadis.mymusicapplication.playerList
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


class currentPlayingFragment : Fragment() {
    var timer: Timer? = null
    private lateinit var playLayout: PlayLayout
    private var binding: FragmentCurrentPlayingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentPlayingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playLayout = binding!!.playLayout
        playMusic(requireContext(), currentMusic!!.filePath)
        startTimer()
        playLayout.startRevealAnimation()
        playLayout.setImageURI(currentMusic!!.coverArtUri)
        playLayout.setOnButtonsClickListener(object : PlayLayout.OnButtonsClickListener {
            override fun onShuffleClicked() {
            }

            override fun onSkipPreviousClicked() {
            }

            override fun onSkipNextClicked() {
            }

            override fun onRepeatClicked() {
            }

            override fun onPlayButtonClicked() {
                if (playLayout.isOpen) {
                    playLayout.startDismissAnimation()
                    playerList.last().pause()
                } else {
                    playLayout.startRevealAnimation()
                    playerList.last().play()
                }
            }
        })
        playLayout.setOnProgressChangedListener(object : PlayLayout.OnProgressChangedListener {
            override fun onPreSetProgress() {
                cancelTimer()
            }

            override fun onProgressChanged(progress: Float) {
                playerList.last().seekTo((playerList.last().duration * progress).toLong())
                startTimer()
            }

        })
    }

    private fun startTimer() {
        timer = Timer()
        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                lifecycleScope.launch {
                    playLayout.setProgress(playerList.last().currentPosition.toFloat() / playerList.last().duration)
                }
            }
        }, 0, 1000)
    }

    private fun cancelTimer() {
        if (timer == null) return
        timer!!.cancel()
        timer = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}