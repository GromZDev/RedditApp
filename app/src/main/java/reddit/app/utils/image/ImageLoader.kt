package q4_android_professional.myapplication.utils

interface ImageLoader<T> {

    fun loadInto(url: String, container: T)

}