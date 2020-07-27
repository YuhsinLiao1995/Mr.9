package com.tina.mr9.others_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tina.mr9.data.Ratings
import com.tina.mr9.data.User
import com.tina.mr9.others_profile.item.OthersLikedFragment
import com.tina.mr9.others_profile.item.OthersRatingFragment


class OthersPagesAdapter(fragmentManager: FragmentManager, val searchUser: User) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OthersRatingFragment().apply {
                arguments = Bundle().apply { putParcelable("searchUser", searchUser) }
            }
            else -> OthersLikedFragment().apply {
                arguments = Bundle().apply { putParcelable("searchUser", searchUser) }
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



