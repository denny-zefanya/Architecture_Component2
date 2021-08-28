package zefanya.denny.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import zefanya.denny.core.data.Resource
import zefanya.denny.core.domain.model.DetailMovie
import zefanya.denny.core.domain.model.DetailTvShow
import zefanya.denny.core.domain.model.Movie
import zefanya.denny.core.domain.model.TvShow
import zefanya.denny.core.domain.repository.ICatalogRepository

class CatalogInteractor(private val catalogRepository: ICatalogRepository) : CatalogUseCase {
    override fun getlistMovies(query: String, isFav: Boolean): Flow<Resource<List<Movie>>> =
        catalogRepository.getlistMovies(query, isFav)

    override fun getListTvShows(query: String, isFav: Boolean): Flow<Resource<List<TvShow>>> =
        catalogRepository.getListTvShows(query, isFav)

    override fun getDetailMovie(id: String): Flow<Resource<List<DetailMovie>>> =
        catalogRepository.getDetailMovie(id)

    override fun getDetailTvShow(id: String): Flow<Resource<List<DetailTvShow>>> =
        catalogRepository.getDetailTvShow(id)

    override fun updateHeaderMovie(idMovie: String) = catalogRepository.updateHeaderMovie(idMovie)

    override fun getStatusMovie(idMovie: String): Flow<Boolean> =
        catalogRepository.getStatusMovie(idMovie)

    override fun updateHeaderTvShow(idTv: String) = catalogRepository.updateHeaderTvShow((idTv))

    override fun getStatusTvShow(idTv: String): Flow<Boolean> =
        catalogRepository.getStatusTvShow(idTv)

    override fun deleteMovie() = catalogRepository.deleteMovie()

    override fun deleteTvShow() = catalogRepository.deleteTvShow()

    override fun deleteDetailMovie() = catalogRepository.deleteDetailMovie()

    override fun deleteDetailTvShow() = catalogRepository.deleteDetailTvShow()

    override fun deleteFavoriteMovie() = catalogRepository.deleteFavoriteMovie()

    override fun deleteFavoritTvShow() = catalogRepository.deleteFavoritTvShow()
}