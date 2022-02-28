package reddit.app.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel(
    @SerializedName("kind") val kind: String? = "",
    @SerializedName("data") val data: RedditDataResponse? = null
) : Parcelable

@Parcelize
data class RedditDataResponse(
    @SerializedName("after") val after: String? = "",
    @SerializedName("dist") val dist: Int? = 0,
    @SerializedName("modhash") val modhash: String? = "",
    @SerializedName("geo_filter") val geo_filter: String? = "",
    @SerializedName("children") val children: List<RedditChildrenResponse>,
    @SerializedName("before") val before: String? = ""

) : Parcelable

@Parcelize
data class RedditChildrenResponse(
    @SerializedName("kind") val kind: String? = "",
    @SerializedName("data") val data: RedditNewsDataResponse
) : Parcelable

@Parcelize
data class RedditNewsDataResponse(
    @SerializedName("title") val title: String,
    @SerializedName("name") val name: String,
    @SerializedName("total_awards_received") val total_awards_received: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("likes") val likes: String,
    @SerializedName("author") val author: String,
    @SerializedName("url") val url: String,
    @SerializedName("subreddit_subscribers") val subreddit_subscribers: Int,
    @SerializedName("created") val created: Int,
    @SerializedName("num_comments") val num_comments: Int
) : Parcelable