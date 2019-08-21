package com.openclassrooms.realestatemanager.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.openclassrooms.realestatemanager.fragments.description.DescriptionFragment
import com.openclassrooms.realestatemanager.fragments.location.LocationFragment


class PageAdapter(mgr: FragmentManager, private val mContext: Context) : FragmentPagerAdapter(mgr) {


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
            -> return DescriptionFragment.newInstance()
            1 //Page number 2
            -> return LocationFragment.newInstance()
            else -> return null
        }
    }
}

