package reddit.app.utils.image

interface ImageLoader<T> {

    fun loadInto(url: String, container: T)

}