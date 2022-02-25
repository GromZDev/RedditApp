package reddit.app.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import reddit.app.base.AppState
import reddit.app.base.BaseViewModel
import reddit.app.interactor.MainInterActor


class MainViewModel(
    private val interActor: MainInterActor
) : BaseViewModel<AppState>() {

    private val livedataToObserve: LiveData<AppState> = _mutableLiveData

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
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}