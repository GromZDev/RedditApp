package reddit.app.repository.reporemote


import reddit.app.data.DataModel

class RepositoryImplementation(private val dataSource: DataSource<DataModel>) :
    Repository<DataModel> {

    /** Coroutines -  */
    override suspend fun getData(): DataModel {
        return dataSource.getData()
    }
}