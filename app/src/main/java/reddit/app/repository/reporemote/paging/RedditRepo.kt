package reddit.app.repository.reporemote.paging

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import reddit.app.data.RedditNewsDataResponse
import reddit.app.repository.BaseInterceptor
import reddit.app.repository.RetrofitImplementation

class RedditRepo {

    private val redditService = RetrofitImplementation().getService(BaseInterceptor.interceptor)


    fun fetchPosts(): Flow<PagingData<RedditNewsDataResponse>> {

        return Pager(
            PagingConfig(pageSize = 15, enablePlaceholders = false)
        ) {
            RedditPagingSource(redditService)
        }.flow
    }
}