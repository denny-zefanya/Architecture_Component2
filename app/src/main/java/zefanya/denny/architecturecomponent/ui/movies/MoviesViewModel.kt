package zefanya.denny.architecturecomponent.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import zefanya.denny.core.data.Resource
import zefanya.denny.core.domain.model.Movie
import zefanya.denny.core.domain.usecase.CatalogUseCase

class MoviesViewModel(private val catalogUseCase: CatalogUseCase) : ViewModel() {
    fun getListMovies(query: String, isFav: Boolean): LiveData<Resource<List<Movie>>> =
        catalogUseCase.getlistMovies(query, isFav).asLiveData()
}