package com.openclassrooms.realestatemanager.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.openclassrooms.realestatemanager.fragments.list.ListFragment

import com.openclassrooms.realestatemanager.fragments.map.MapFragment


class MainPageAdapter(mgr: FragmentManager, private val mContext: Context) : FragmentPagerAdapter(mgr) {


    /**
     * Page numbers.
     *
     * @return number of pages
     */
    override fun getCount(): Int {
        return 2
    }

    /**
     * Set the fragment to show.
     *
     * @param position actual view
     * @return the fragment
     */
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 //Page number 1
            -> return ListFragment.newInstance()
            1 //Page number 2
            -> return MapFragment.newInstance()
            else -> return null
        }
    }
}

