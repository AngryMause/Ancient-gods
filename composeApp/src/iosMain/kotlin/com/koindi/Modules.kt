package com.koindi

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.data.datastore.AppOptionPreferences
import com.data.datastore.AppOptionPreferencesImpl
import com.data.datastore.dataStorePreferences
import com.data.media.GameAudioPlayer
import com.data.media.GameAudioPlayerImpl
import com.data.media.mainsound.MainAudionPlayer
import com.data.media.mainsound.MainAudionPlayerImpl
import com.repository.GameRepository
import com.ui.presentation.games.GameViewModel
import com.ui.presentation.navigation.AppNavViewModel
import com.ui.presentation.option.OptionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DISPATCHER_IO = "IO_Dispatcher"
private const val DISPATCHER_DEFAULT = "default_dispatcher"

val viewModelModule = module {
    viewModel { GameViewModel(get(), get(named(DISPATCHER_IO))) }
    viewModel { OptionViewModel(get(), get(named(DISPATCHER_IO))) }
    viewModel {
        AppNavViewModel(
            appOptionPreferences = get(),
            mainAudionPlayer = get(),
            dispatcherIo = get(named(DISPATCHER_IO))
        )
    }
}
val factoryModule = module {
    factory { GameRepository(get(), get(), get(named(DISPATCHER_DEFAULT))) }
}

val singleModule = module {
    single<AppOptionPreferences> { AppOptionPreferencesImpl(createDatastore) }
    single<GameAudioPlayer> { GameAudioPlayerImpl() }
    single<MainAudionPlayer> { MainAudionPlayerImpl() }
    single(named(DISPATCHER_IO)) {
        Dispatchers.IO
    }
    single(named(DISPATCHER_DEFAULT)) {
        Dispatchers.Default
    }
}

private val createDatastore: DataStore<Preferences> = dataStorePreferences(
    coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
    migrations = emptyList()
)


fun initKoin() {
    startKoin { modules(viewModelModule, factoryModule, singleModule) }
}