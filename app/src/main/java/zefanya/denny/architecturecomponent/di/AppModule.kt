package zefanya.denny.architecturecomponent.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import zefanya.denny.architecturecomponent.ui.content.ContentViewModel
import zefanya.denny.architecturecomponent.ui.movies.MoviesViewModel
import zefanya.denny.architecturecomponent.ui.setting.SettingViewModel
import zefanya.denny.architecturecomponent.ui.tvShows.TvShowsViewModel
import zefanya.denny.core.domain.usecase.CatalogInteractor
import zefanya.denny.core.domain.usecase.CatalogUseCase

val useCaseModule = module {
    factory<CatalogUseCase> { CatalogInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { TvShowsViewModel(get()) }
    viewModel { ContentViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}