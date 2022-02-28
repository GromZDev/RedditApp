package reddit.app.repository.repolocal

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import reddit.app.base.AppState
import reddit.app.data.DataModel
import reddit.app.data.RedditNewsDataResponse

class RepositoryImplementationLocal(
    private val dataSource: DataSourceLocal<DataModel>
) : RepositoryLocal<DataModel, Flow<PagingData<RedditNewsDataResponse>>> {

    override suspend fun getData(): DataModel = dataSource.getData()

    override suspend fun saveToDB(appState: AppState) = dataSource.saveToDB(appState)

}