package com.hadis.mymusicapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cleveroad.play_widget.PlayLayout
import com.hadis.mymusicapplication.databinding.FragmentCurrentPlayingBinding

class currentPlayingFragment : Fragment() {
    lateinit var playLayout : PlayLayout
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
        platMusic(requireContext() , currentMusic!!.filePath)
        playLayout.startRevealAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}