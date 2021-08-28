package zefanya.denny.core.domain.repository

import kotlinx.coroutines.flow.Flow
import zefanya.denny.core.data.Resource
import zefanya.denny.core.domain.model.DetailMovie
import zefanya.denny.core.domain.model.DetailTvShow
import zefanya.denny.core.domain.model.Movie
import zefanya.denny.core.domain.model.TvShow

interface ICatalogRepository {
    fun getlistMovies(query: String, isFav: Boolean): Flow<Resource<List<Movie>>>

    fun getListTvShows(query: String, isFav: Boolean): Flow<Resource<List<TvShow>>>

    fun getDetailMovie(id: String): Flow<Resource<List<DetailMovie>>>

    fun getDetailTvShow(id: String): Flow<Resource<List<DetailTvShow>>>

    fun updateHeaderMovie(idMovie: String)

    fun getStatusMovie(idMovie: String): Flow<Boolean>

    fun updateHeaderTvShow(idTv: String)

    fun getStatusTvShow(idTv: String): Flow<Boolean>

    fun deleteMovie()

    fun deleteTvShow()

    fun deleteDetailMovie()

    fun deleteDetailTvShow()

    fun deleteFavoriteMovie()

    fun deleteFavoritTvShow()
}