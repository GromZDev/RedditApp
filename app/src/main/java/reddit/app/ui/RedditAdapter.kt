package reddit.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import reddit.app.R
import reddit.app.data.RedditNewsDataResponse
import reddit.app.utils.DiffUtilCallBack
import reddit.app.utils.image.ImageLoader

class RedditAdapter(
    val imageLoader: ImageLoader<ShapeableImageView>
) :
    PagingDataAdapter<RedditNewsDataResponse, RedditAdapter.RedditViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main_fragment_rv, parent, false)
        return RedditViewHolder(view)
    }

    override fun onBindViewHolder(holder: RedditViewHolder, position: Int) {
        getItem(position)?.let { redditPost ->
            holder.bindPost(redditPost)
        }
    }

    inner class RedditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val authorText: TextView = itemView.findViewById(R.id.header_textview_recycler_item)
        private val imageView: ShapeableImageView = itemView.findViewById(R.id.item_word_image)
        private val scoreText: TextView = itemView.findViewById(R.id.star_textview)
        private val description: TextView =
            itemView.findViewById(R.id.description_textview_recycler_item)
        private val message: TextView = itemView.findViewById(R.id.message_textview)

        fun bindPost(redditPost: RedditNewsDataResponse) {
            with(redditPost) {
                authorText.text = author
                imageLoader.loadInto(
                    url, imageView
                )
                description.text = title
                scoreText.text = score.toString()
                message.text = num_comments.toString()

            }
        }
    }
}