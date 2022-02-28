package reddit.app.repository.repolocal

import reddit.app.base.AppState
import reddit.app.repository.reporemote.Repository

interface RepositoryLocal<T, V> : Repository<T, V> {
    suspend fun saveToDB(appState: AppState)
}