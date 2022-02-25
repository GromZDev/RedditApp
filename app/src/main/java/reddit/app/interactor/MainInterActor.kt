package reddit.app.interactor

import reddit.app.base.AppState
import reddit.app.base.LogicInterActor
import reddit.app.data.DataModel
import reddit.app.repository.reporemote.Repository

class MainInterActor(
    private val remoteRepository: Repository<DataModel?>,
) : LogicInterActor<AppState> {

    /** Coroutines -  */
    override suspend fun getData(fromRemoteSource: Boolean): AppState {

        val appState: AppState
        appState = AppState.Success(remoteRepository.getData())
        return appState
    }
}