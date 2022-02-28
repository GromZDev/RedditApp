package reddit.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import reddit.app.base.AppState
import reddit.app.base.BaseViewModel
import reddit.app.data.RedditNewsDataResponse
import reddit.app.interactor.MainInterActor
import reddit.app.repository.reporemote.paging.RedditRepo


class MainViewModel(
    private val interActor: MainInterActor
) : BaseViewModel<AppState>() {

    private val livedataToObserve: LiveData<AppState> = _mutableLiveData
    private val redditRepo = RedditRepo()

    fun fetchPosts(): Flow<PagingData<RedditNewsDataResponse>> {
        return redditRepo.fetchPosts().cachedIn(viewModelScope)
    }

    fun subscribe(): LiveData<AppState> {
        return livedataToObserve
    }

    /** Coroutines -  */
    override fun getData(isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()

        viewModelCoroutineScope.launch { startInterActor(isOnline) }
    }

    private suspend fun startInterActor(isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            _mutableLiveData.postValue(
                interActor.getData(
                    isOnline
                )
            )
        }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(data = null, flow = flowOf())
        super.onCleared()
    }
}