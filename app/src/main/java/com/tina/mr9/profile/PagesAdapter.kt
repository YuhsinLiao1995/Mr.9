package com.tina.mr9.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tina.mr9.data.User
import com.tina.mr9.profile.item.BarLikedFragment
import com.tina.mr9.profile.item.LikedFragment
import com.tina.mr9.profile.item.MyRatingFragment


class PagesAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyRatingFragment().apply {
                arguments = Bundle().apply { putParcelable("userKey", User()) }
            }
            1 -> LikedFragment().apply {
                arguments = Bundle().apply { putParcelable("userKey", User()) }
            }

            else -> BarLikedFragment().apply {
                arguments = Bundle().apply { putParcelable("userKey", User()) }
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "My Rating"
            1 -> "My Drink"
            else -> "My Bar"

        }
    }

}



