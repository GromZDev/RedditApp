package reddit.app.repository.reporemote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import reddit.app.data.RedditNewsDataResponse
import reddit.app.repository.api.ApiService
import retrofit2.HttpException
import java.io.IOException

class RedditPagingSource(private val redditService: ApiService) :
    PagingSource<String, RedditNewsDataResponse>() {

    override val keyReuseSupported: Boolean = true /** Для того, чтобы не было ошибки при скролле */

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditNewsDataResponse> {
        return try {

            val response = redditService.searchAsync(loadSize = params.loadSize)

            val listing = response.await()
            val redditPosts = listing.data?.children?.map { it.data }

            LoadResult.Page(

                redditPosts ?: listOf(),

                listing.data?.before,
                listing.data?.after
            )
        } catch (exception: IOException) { // 6
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, RedditNewsDataResponse>): String? {
        TODO("Not yet implemented")
    }
}