package reddit.app.interactor

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import reddit.app.base.AppState
import reddit.app.base.LogicInterActor
import reddit.app.data.DataModel
import reddit.app.data.RedditNewsDataResponse
import reddit.app.repository.repolocal.RepositoryLocal
import reddit.app.repository.reporemote.Repository
import reddit.app.repository.reporemote.paging.RedditRepo

class MainInterActor(
    private val remoteRepository: Repository<DataModel?, Flow<PagingData<RedditNewsDataResponse>>>,
    private val localRepository: RepositoryLocal<DataModel?, Flow<PagingData<RedditNewsDataResponse>>>,
) : LogicInterActor<AppState> {
    private val redditRepo = RedditRepo()

    /** Coroutines -  */
    override suspend fun getData(fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            Log.d("TAG", "Начинаю получать данные из апи <<<<<<<<<<<<<<<<<<<")
            appState = AppState.Success(remoteRepository.getData(), redditRepo.fetchPosts())

            Log.d(
                "TAG",
                "Количество: " + remoteRepository.getData()?.data?.children?.size.toString() + " <<<<<<<<<<<<<<<<<<<"
            )
            Log.d("TAG", "Начинаю записывать в БД <<<<<<<<<<<<<<<<<<<")
            localRepository.saveToDB(appState)
            Log.d("TAG", "Запись в БД успешна <<<<<<<<<<<<<<<<<<<")
            Log.d(
                "TAG",
                "Количество: " + localRepository.getData()?.data?.children?.size.toString() + " <<<<<<<<<<<<<<<<<<<"
            )
        } else {
            Log.d("TAG", "Начинаю получать данные из БД <<<<<<<<<<<<<<<<<<<")
            appState = AppState.Success(localRepository.getData(), redditRepo.fetchPosts())
            Log.d("TAG", "Данные из БД получены <<<<<<<<<<<<<<<<<<<")
            Log.d(
                "TAG",
                localRepository.getData()?.data?.children?.size.toString() + " <<<<<<<<<<<<<<<<<<<"
            )
        }
        return appState
    }
}