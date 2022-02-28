package reddit.app.repository.reporemote

/** Репозиторий представляет собой слой получения
 * и хранения данных, которые он передаёт интерактору */
interface Repository<T, V> {

    /** Coroutines -  */
    suspend fun getData(): T


}