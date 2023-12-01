package com.hadis.mymusicapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadis.mymusicapplication.databinding.FragmentFavoriteSongsBinding
import kotlinx.coroutines.launch

var menuItemInFavoriteSongsFragment: MenuItem? = null

class FavoriteSongsFragment : Fragment() {
    private var binding: FragmentFavoriteSongsBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteSongsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        menuItemInFavoriteSongsFragment = menu.findItem(R.id.searchItem)
        val searchItem = menuItemInFavoriteSongsFragment!!.actionView as SearchView
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!)
                return false
            }

        })
    }

    private fun filter(input: String) {
        val temList = mutableListOf<Music>()
        for (music in mainList) {
            if (music.title.lowercase().contains(input.lowercase()))
                temList.add(music)
        }
        ((binding!!.recycleViewFavoriteSongs.adapter) as? MusicAdaptor)?.filterList(temList)
    }


    override fun onResume() {
        super.onResume()
        mainList.clear()
        mainList.addAll(favoriteSongs)
    }

    private fun initialRecycleView() {
        favoriteSongs.clear()
        lifecycleScope.launch {
            for (music in allMusicList) {
                val key = "${music.singerName}#${music.title}"
                if (key == readFromDataStore(requireContext() ,key )){
                    favoriteSongs.add(music)
                    music.isFavorite = true
                }
            }


            val recycleView = binding!!.recycleViewFavoriteSongs

            recycleView.layoutManager = LinearLayoutManager(requireContext())
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            recycleView.addItemDecoration(dividerItemDecoration)

            val musicAdaptor = MusicAdaptor(favoriteSongs, requireContext())

            recycleView.adapter = musicAdaptor

            mainList.clear()
            mainList.addAll(favoriteSongs)
        }
    }
}