package com.tina.mr9.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tina.mr9.data.User
import com.tina.mr9.profile.item.LikedFragment
import com.tina.mr9.profile.item.MyRatingFragment


class PagesAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return MyRatingFragment().apply {
                arguments = Bundle().apply { putParcelable("userKey", User()) }
            }
            else -> return LikedFragment().apply {
                arguments = Bundle().apply { putParcelable("userKey", User()) }
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "My Rating"
            else -> return "Liked"

        }
    }

}



