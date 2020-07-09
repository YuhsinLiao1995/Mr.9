package com.tina.mr9.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tina.mr9.search.item.SearchItemFragment

/**
 * Created by Yuhsin Liao Jul. 2020.
 */
class SearchAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return SearchItemFragment(SearchTypeFilter.values()[position])
    }

    override fun getCount() = SearchTypeFilter.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        return SearchTypeFilter.values()[position].value
    }
}
