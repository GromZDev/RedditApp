package reddit.app.koin

import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import reddit.app.data.DataModel
import reddit.app.interactor.MainInterActor
import reddit.app.repository.RetrofitImplementation
import reddit.app.repository.repolocal.RepositoryImplementationLocal
import reddit.app.repository.repolocal.RepositoryLocal
import reddit.app.repository.reporemote.Repository
import reddit.app.repository.reporemote.RepositoryImplementation
import reddit.app.repository.room.HistoryDataBase
import reddit.app.repository.room.RoomDataBaseImplementation
import reddit.app.ui.MainFragment
import reddit.app.viewmodel.MainViewModel

/** application - тут хранятся зависимости, используемые во всем приложении */
val application = module {

    single<Repository<DataModel>> {
        RepositoryImplementation(RetrofitImplementation())
    }

    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<RepositoryLocal<DataModel>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }

}

/** Объявляем скоуп для главного фрагмента. Также и viewModel */
val mainScreen = module {
    scope(named<MainFragment>()) {
        scoped { MainInterActor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}


