package reddit.app.repository.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    indices = [Index(value = arrayOf("title", "name", "total_awards_received",
        "score","likes", "author", "url", "subreddit_subscribers", "created", "num_comments"  ), unique = true)]
)
class HistoryEntity(

    @field:PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @field:ColumnInfo("title")
    val title: String?,
    @field:ColumnInfo("name")
    val name: String?,
    @field:ColumnInfo("total_awards_received")
    val total_awards_received: Int?,
    @field:ColumnInfo("score")
    val score: Int?,
    @field:ColumnInfo("likes")
    val likes: String?,
    @field:ColumnInfo("author")
    val author: String?,
    @field:ColumnInfo("url")
    val url: String?,
    @field:ColumnInfo("subreddit_subscribers")
    val subreddit_subscribers: Int?,
    @field:ColumnInfo("created")
    val created: Int?,
    @field:ColumnInfo("num_comments")
    val num_comments: Int?
)



