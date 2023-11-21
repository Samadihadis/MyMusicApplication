package com.hadis.mymusicapplication11

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cleveroad.play_widget.PlayLayout
import com.hadis.mymusicapplication.currentMusic
import com.hadis.mymusicapplication.databinding.FragmentCurrentPlayingBinding
import com.hadis.mymusicapplication.playMusic
import com.hadis.mymusicapplication.playerList

class currentPlayingFragment : Fragment() {
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
        playLayout.startRevealAnimation()
        playLayout.setImageURI(currentMusic!!.coverArtUri)
        playLayout.setOnButtonsClickListener(object : PlayLayout.OnButtonsClickListener{
            override fun onShuffleClicked() {
            }

            override fun onSkipPreviousClicked() {
            }

            override fun onSkipNextClicked() {
            }

            override fun onRepeatClicked() {
            }

            override fun onPlayButtonClicked() {
                if (playLayout.isOpen){
                    playLayout.startDismissAnimation()
                    playerList.last().pause()
                }
                else{
                    playLayout.startRevealAnimation()
                    playerList.last().play()
                }
            }
        })
        playLayout.setOnProgressChangedListener(object  : PlayLayout.OnProgressChangedListener{
            override fun onPreSetProgress() {

            }

            override fun onProgressChanged(progress: Float) {
                playerList.last().seekTo((playerList.last().duration * progress).toLong())
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}