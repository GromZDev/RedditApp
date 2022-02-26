package reddit.app.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import reddit.app.repository.room.dao.HistoryDao
import reddit.app.repository.room.entities.HistoryEntity

@Database(
    entities = [HistoryEntity::class], version = 1, exportSchema = true
)
abstract class HistoryDataBase : RoomDatabase() {
    /**  Возвращаем наш DAO */
    abstract fun historyDao(): HistoryDao

}