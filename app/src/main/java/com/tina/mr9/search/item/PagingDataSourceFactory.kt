package com.tina.mr9.search.item

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.tina.mr9.data.Search
import com.tina.mr9.search.SearchTypeFilter

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * Factory for PagingDataSource
 */
class PagingDataSourceFactory(val type: SearchTypeFilter) : DataSource.Factory<String, Search>() {

    val sourceLiveData = MutableLiveData<PagingDataSource>()

    override fun create(): DataSource<String, Search> {
        val source = PagingDataSource(type)
        sourceLiveData.postValue(source)
        return source
    }
}