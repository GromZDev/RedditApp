package reddit.app.interactor

import android.util.Log
import reddit.app.base.AppState
import reddit.app.base.LogicInterActor
import reddit.app.data.DataModel
import reddit.app.repository.repolocal.RepositoryLocal
import reddit.app.repository.reporemote.Repository

class MainInterActor(
    private val remoteRepository: Repository<DataModel?>,
    private val localRepository: RepositoryLocal<DataModel?>
) : LogicInterActor<AppState> {

    /** Coroutines -  */
    override suspend fun getData(fromRemoteSource: Boolean): AppState {

        val appState: AppState
        if (fromRemoteSource) {
            Log.d("TAG", "Начинаю получать данные из апи <<<<<<<<<<<<<<<<<<<")
            appState = AppState.Success(remoteRepository.getData())
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
            appState = AppState.Success(localRepository.getData())
            Log.d("TAG", "Данные из БД получены <<<<<<<<<<<<<<<<<<<")
            Log.d(
                "TAG",
                localRepository.getData()?.data?.children?.size.toString() + " <<<<<<<<<<<<<<<<<<<"
            )
        }
        return appState
    }
}