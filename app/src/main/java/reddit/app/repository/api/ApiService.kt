package reddit.app.repository.api


import kotlinx.coroutines.Deferred
import reddit.app.data.DataModel
import retrofit2.http.GET

interface ApiService {
    @GET("/r/aww/hot.json")
            /** Coroutines - возвращаем данные через Deferred */
    fun searchAsync(): Deferred<DataModel>
}