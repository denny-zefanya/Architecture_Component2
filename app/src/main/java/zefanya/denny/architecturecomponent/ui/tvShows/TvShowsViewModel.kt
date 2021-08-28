package zefanya.denny.architecturecomponent.ui.tvShows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import zefanya.denny.core.domain.usecase.CatalogUseCase

class TvShowsViewModel(private val catalogUseCase: CatalogUseCase) : ViewModel() {
    fun getListTvShows(query: String, isFav: Boolean) =
        catalogUseCase.getListTvShows(query, isFav).asLiveData()
}