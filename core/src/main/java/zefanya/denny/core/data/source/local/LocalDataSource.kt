package zefanya.denny.core.data.source.local

import kotlinx.coroutines.flow.Flow
import zefanya.denny.core.data.source.local.entity.DetailMovieEntity
import zefanya.denny.core.data.source.local.entity.DetailTvShowEntity
import zefanya.denny.core.data.source.local.entity.MovieEntity
import zefanya.denny.core.data.source.local.entity.TvShowEntity
import zefanya.denny.core.data.source.local.room.MovieDao
import zefanya.denny.core.utils.SortUtils

class LocalDataSource constructor(private val movieDao: MovieDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao).apply {
                INSTANCE = this
            }
    }

    fun getMovieList(sort: String, isFav: Boolean, source: String): Flow<List<MovieEntity>> =
        movieDao.getTopRatedMovieList(SortUtils.getSortedQuery(sort, isFav, source))

    fun getDetailMovie(id: String): Flow<List<DetailMovieEntity>> = movieDao.getDetailMovie(id)

    fun getTvShowList(sort: String, isFav: Boolean, source: String): Flow<List<TvShowEntity>> =
        movieDao.getTopRatedTvShowList(SortUtils.getSortedQuery(sort, isFav, source))

    fun getDetailTvShow(id: String): Flow<List<DetailTvShowEntity>> = movieDao.getDetailTvShow(id)

    suspend fun insertHeaderMovie(list: List<MovieEntity>) = movieDao.insertHeaderMovie(list)

    suspend fun insertDetailMovie(detail: DetailMovieEntity) = movieDao.insertDetailMovie(detail)

    suspend fun insertHeaderTvShow(list: List<TvShowEntity>) = movieDao.insertHeaderTvShow(list)

    suspend fun insertDetailTvShow(detail: DetailTvShowEntity) = movieDao.insertDetailTvShow(detail)

    fun updateHeaderMovie(idMovie: String) = movieDao.updateHeaderMovie(idMovie)

    fun getStatusMovie(idMovie: String): Flow<Boolean> = movieDao.getStatusMovie(idMovie)

    fun updateHeaderTvShow(idTv: String) = movieDao.updateHeaderTvShow(idTv)

    fun getStatusTvShow(idTv: String): Flow<Boolean> = movieDao.getStatusTvShow(idTv)

    fun deleteMovie() = movieDao.deleteMovie()

    fun deleteTvShow() = movieDao.deleteTvShow()

    fun deleteDetailMovie() = movieDao.deleteDetailMovie()

    fun deleteDetailTvShow() = movieDao.deleteDetailTvShow()

    fun deleteFavoriteMovie() = movieDao.deleteFavouriteMovie()

    fun deleteFavoritTvShow() = movieDao.deleteFavouriteTvShow()
}