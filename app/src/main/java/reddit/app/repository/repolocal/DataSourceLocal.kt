package reddit.app.repository.repolocal

import reddit.app.base.AppState
import reddit.app.repository.reporemote.DataSource

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
}
