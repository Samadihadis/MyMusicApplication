package com.hadis.mymusicapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadis.mymusicapplication.databinding.FragmentAllMusicBinding
import com.karumi.dexter.Dexter
import android.Manifest
import android.widget.Toast
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class AllMusicFragment : Fragment() {
    private var binding: FragmentAllMusicBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMusicBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPermission()
    }

    private fun getPermission() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_MEDIA_AUDIO)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    initialRecycleView()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }

    private fun initialRecycleView(){
        getAllAudioFromDevice(requireContext())
        val recycleView = binding!!.recycleViewAllSongs
        val musicList = MusicAdaptor(allMusicList , requireContext() )
        recycleView.adapter = musicList
        mainList.clear()
        mainList.addAll(allMusicList)
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext() , DividerItemDecoration.VERTICAL)
        recycleView.addItemDecoration(dividerItemDecoration)
    }

    override fun onResume() {
        super.onResume()
        mainList.clear()
        mainList.addAll(allMusicList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}