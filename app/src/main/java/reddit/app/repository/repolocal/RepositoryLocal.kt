package reddit.app.repository.repolocal

import reddit.app.base.AppState
import reddit.app.repository.reporemote.Repository

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
}