package reddit.app.repository.reporemote


import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import reddit.app.data.DataModel
import reddit.app.data.RedditNewsDataResponse

class RepositoryImplementation(private val dataSource: DataSource<DataModel>) :
    Repository<DataModel, Flow<PagingData<RedditNewsDataResponse>>> {

    /** Coroutines -  */
    override suspend fun getData(): DataModel {
        return dataSource.getData()
    }
}