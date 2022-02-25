package reddit.app.base



interface LogicInterActor<T> {
    /** Coroutines -  */
    suspend fun getData(fromRemoteSource: Boolean): T
}