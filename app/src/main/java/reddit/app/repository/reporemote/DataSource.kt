package reddit.app.repository.reporemote


/** Источник данных для репозитория (Интернет, БД и т. п.) */
interface DataSource<T> {

    /** Coroutines -  */
    suspend fun getData(): T

}