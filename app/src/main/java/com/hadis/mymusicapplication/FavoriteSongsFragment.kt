package com.hadis.mymusicapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadis.mymusicapplication.databinding.FragmentFavoriteSongsBinding


class FavoriteSongsFragment : Fragment() {
    private var binding: FragmentFavoriteSongsBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteSongsBinding.inflate(inflater, container, false)
        return binding!!.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialRecycleView()
    }

    override fun onResume() {
        super.onResume()
        initialRecycleView()
    }

    private fun initialRecycleView(){
        val recycleView = binding!!.recycleViewFavoriteSongs
        val musicList = MusicAdaptor(favoriteSongs , requireContext() )
        mainList.clear()
        mainList.addAll(favoriteSongs)
        recycleView.adapter = musicList
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext() , DividerItemDecoration.VERTICAL)
        recycleView.addItemDecoration(dividerItemDecoration)
    }
}