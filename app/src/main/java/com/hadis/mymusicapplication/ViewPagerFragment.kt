package com.hadis.mymusicapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.hadis.mymusicapplication.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {
    private var binding: FragmentViewPagerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding!!.viewPager
        val tabLayout = binding!!.tabLayout
        val adaptor = ViewPagerAdaptor(
            mutableListOf(AllMusicFragment(), FavoriteSongsFragment()),
            requireActivity().supportFragmentManager,
            lifecycle
        )
        viewPager.adapter = adaptor
        tabLayout.addTab(tabLayout.newTab().setText("تمامی آهنگ ها"))
        tabLayout.addTab(tabLayout.newTab().setText("آهنگ های مورد علاقه"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewPager.registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

