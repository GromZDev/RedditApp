package reddit.app.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import q4_android_professional.myapplication.utils.ImageLoader
import reddit.app.data.RedditChildrenResponse
import reddit.app.databinding.ItemMainFragmentRvBinding

class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<RedditChildrenResponse>,
    val imageLoader: ImageLoader<ShapeableImageView>
) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {


    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<RedditChildrenResponse>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerItemViewHolder(
            ItemMainFragmentRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(private val vb: ItemMainFragmentRvBinding) :
        RecyclerView.ViewHolder(vb.root) {


        fun bind(data: RedditChildrenResponse) = with(vb) {
            if (layoutPosition != RecyclerView.NO_POSITION) {

                headerTextviewRecyclerItem.text = data.data.author
                imageLoader.loadInto(
                    data.data.url, itemWordImage
                )
                descriptionTextviewRecyclerItem.text = data.data.title
                itemView.setOnClickListener { openInNewWindow(data) }
                starTextview.text = data.data.score.toString()
                messageTextview.text = data.data.num_comments.toString()
            }
        }
    }

    private fun openInNewWindow(listItemData: RedditChildrenResponse) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: RedditChildrenResponse)
    }

}