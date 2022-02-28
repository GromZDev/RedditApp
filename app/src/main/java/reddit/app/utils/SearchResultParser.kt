package reddit.app.utils

import reddit.app.base.AppState
import reddit.app.data.DataModel
import reddit.app.data.RedditChildrenResponse
import reddit.app.data.RedditDataResponse
import reddit.app.data.RedditNewsDataResponse
import reddit.app.repository.room.entities.HistoryEntity


/** Room +++++++++++++++++++++++++++++++++++++++++++++*/
fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): DataModel {
    val receivedDataFromRoom = mutableListOf<RedditChildrenResponse>()

    for (entity in list){
        receivedDataFromRoom.add(RedditChildrenResponse("", RedditNewsDataResponse(
            entity.title ?: "",
            entity.name ?: "",
            entity.total_awards_received ?: 0,
            entity.score ?: 0,
            entity.likes ?: "",
            entity.author ?: "",
            entity.url ?: "",
            entity.subreddit_subscribers ?: 0,
            entity.created ?: 0,
            entity.num_comments ?: 0
        )))
    }
    val searchResult = mutableListOf<DataModel>()
    if (!list.isNullOrEmpty()) {
            searchResult.add(
                DataModel(
                    kind = "kind", RedditDataResponse(
                        after = "after", dist = 0, modhash = "modhash", geo_filter = "geo",
                        children = receivedDataFromRoom, before = "before"
                    )
                )
            )
    }
    return searchResult[0]
}

fun convertDataModelSuccessToEntity(appState: AppState): List<HistoryEntity>? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data?.data?.children
            val entityResult = mutableListOf<HistoryEntity>()
            if (searchResult?.isEmpty() == true || searchResult?.get(
                    0)?.data?.title.isNullOrEmpty()
            ) {
                null
            } else {
                if (searchResult != null) {
                    for (entity in searchResult) {
                        entityResult.add(
                            HistoryEntity(
                                title = entity.data.title,
                                name = entity.data.name,
                                total_awards_received = entity.data.total_awards_received,
                                score = entity.data.score,
                                likes = entity.data.likes,
                                author = entity.data.author,
                                url = entity.data.url,
                                subreddit_subscribers = entity.data.subreddit_subscribers,
                                created = entity.data.created,
                                num_comments = entity.data.num_comments
                            )
                        )

                    }
                }
            return  entityResult
            }
        }
        else -> null
    }
}
/** ++++++++++++++++++++++++++++++++++++++++++++++++ */

