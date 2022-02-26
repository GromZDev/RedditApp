package reddit.app.utils

import reddit.app.base.AppState
import reddit.app.data.DataModel
import reddit.app.data.RedditChildrenResponse
import reddit.app.data.RedditDataResponse
import reddit.app.data.RedditNewsDataResponse
import reddit.app.repository.room.entities.HistoryEntity


/** Room +++++++++++++++++++++++++++++++++++++++++++++*/
fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): DataModel {
    var searchResult = listOf<DataModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult = listOf(
                DataModel(
                    kind = "kind", RedditDataResponse(
                        after = "after", dist = 0, modhash = "modhash", geo_filter = "geo",
                        children = listOf(
                            RedditChildrenResponse(
                                kind = "kind", RedditNewsDataResponse(
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
                                )
                            )
                        ), before = "before"
                    )
                )
            )
        }
    }
    return searchResult[0]
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult?.data?.children?.isEmpty() == true || searchResult?.data?.children?.get(
                    0
                )?.data?.title.isNullOrEmpty()
            ) {
                null
            } else {
                searchResult?.data?.children?.get(0)?.data?.title?.let {
                    HistoryEntity(
                        title = it,
                        name = searchResult.data.children[0].data.name,
                        total_awards_received = searchResult.data.children[0].data.total_awards_received,
                        score = searchResult.data.children[0].data.score,
                        likes = searchResult.data.children[0].data.likes,
                        author = searchResult.data.children[0].data.author,
                        url = searchResult.data.children[0].data.url,
                        subreddit_subscribers = searchResult.data.children[0].data.subreddit_subscribers,
                        created = searchResult.data.children[0].data.created,
                        num_comments = searchResult.data.children[0].data.num_comments
                    )
                }
            }
        }
        else -> null
    }
}
/** ++++++++++++++++++++++++++++++++++++++++++++++++ */

