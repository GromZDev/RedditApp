package reddit.app.base

import reddit.app.data.DataModel
import reddit.app.data.RedditChildrenResponse
import reddit.app.data.RedditDataResponse
import reddit.app.data.RedditNewsDataResponse

sealed class AppState {

    data class Success(val data: DataModel?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()

}