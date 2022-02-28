package reddit.app.repository.repolocal

import reddit.app.base.AppState
import reddit.app.data.DataModel

class RepositoryImplementationLocal(
    private val dataSource: DataSourceLocal<DataModel>
) : RepositoryLocal<DataModel> {

    override suspend fun getData(): DataModel = dataSource.getData()

    override suspend fun saveToDB(appState: AppState) = dataSource.saveToDB(appState)

}