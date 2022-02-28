package reddit.app.base

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import reddit.app.data.DataModel
import reddit.app.data.RedditNewsDataResponse

sealed class AppState {

    data class Success(val data: DataModel?, val flow: Flow<PagingData<RedditNewsDataResponse>>) :
        AppState()

    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()

}