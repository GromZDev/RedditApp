package reddit.app.repository.room

import android.util.Log
import reddit.app.base.AppState
import reddit.app.data.DataModel
import reddit.app.repository.repolocal.DataSourceLocal
import reddit.app.repository.room.dao.HistoryDao
import reddit.app.utils.convertDataModelSuccessToEntity
import reddit.app.utils.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<DataModel> {
    /** Coroutines -  */
       override suspend fun getData(): DataModel =
           mapHistoryEntityToSearchResult(historyDao.all())

    /** Сохраняем слово в БД для интерактора */
    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
