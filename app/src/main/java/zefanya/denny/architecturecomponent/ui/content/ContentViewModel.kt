package zefanya.denny.architecturecomponent.ui.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import zefanya.denny.core.data.Resource
import zefanya.denny.core.domain.model.DetailMovie
import zefanya.denny.core.domain.model.DetailTvShow
import zefanya.denny.core.domain.usecase.CatalogUseCase

class ContentViewModel(private val catalogUseCase: CatalogUseCase) : ViewModel() {

    fun generateDetailMovie(index: String): LiveData<Resource<List<DetailMovie>>> =
        catalogUseCase.getDetailMovie(index).asLiveData()

    fun generateDetailTvShow(index: String): LiveData<Resource<List<DetailTvShow>>> =
        catalogUseCase.getDetailTvShow(index).asLiveData()

    fun updateHeaderMovie(idMovie: String) {
        catalogUseCase.updateHeaderMovie(idMovie)
    }

    fun getStatusMovie(idMovie: String): LiveData<Boolean> =
        catalogUseCase.getStatusMovie(idMovie).asLiveData()

    fun updateHeaderTvShow(idTv: String) {
        catalogUseCase.updateHeaderTvShow(idTv)
    }

    fun getStatusTvShow(idTv: String): LiveData<Boolean> =
        catalogUseCase.getStatusTvShow(idTv).asLiveData()
}