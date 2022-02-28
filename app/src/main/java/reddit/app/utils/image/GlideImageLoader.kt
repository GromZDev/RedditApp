package reddit.app.utils.image

import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.imageview.ShapeableImageView
import reddit.app.R

class GlideImageLoader : ImageLoader<ShapeableImageView> {
    override fun loadInto(url: String, container: ShapeableImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .error(R.drawable.ic_no_image)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    bitmap: Bitmap?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(container)

    }
}